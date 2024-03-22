#!/bin/bash

# dependencies: AWS CLI, keytool, jq, psql

copy_to_tmp() {

    S3_DIR=$1
    FILE=$2
    TARGET=$3

    if [ -z "$TARGET" ]; then TARGET=$FILE; fi

    aws s3 cp s3://${ENVIRONMENT}-deployment/$S3_DIR/$FILE $TMPDIR/$TARGET >/dev/null
}

dump_cert_from_db_to_tmp() {

    SQL_FILE="sql/auth_certificate.sql"
    CLIENT_ID=$1
    TARGET=$2

    if [ -z "$TARGET" ]; then TARGET="db_certificate.pem"; fi

    echo "-----BEGIN CERTIFICATE-----" > $TMPDIR/$TARGET
    psql -v client_val="'${CLIENT_ID}'" < $SQL_FILE | tail -n 3 | head -n 1 >> $TMPDIR/$TARGET
    echo "-----END CERTIFICATE-----" >> $TMPDIR/$TARGET
}

dump_cert() {

    CERT_FILE=$1

    openssl x509 -in $TMPDIR/$CERT_FILE -noout -text
}

get_valid_from() {

    CERT_FILE=$1
    openssl x509 -in $TMPDIR/$CERT_FILE -noout -startdate | cut -d '=' -f 2
}

get_expiration() {

    CERT_FILE=$1
    openssl x509 -in $TMPDIR/$CERT_FILE -noout -enddate | cut -d '=' -f 2
}

get_password() {
    PARAMETER_NAME=$1
    aws ssm get-parameter --name $PARAMETER_NAME --with-decryption | jq -r .Parameter.Value
}

list_entries() {
    KEYSTORE=$1
    PASSWORD=$2

    keytool -keystore $TMPDIR/$KEYSTORE -list -storepass "$PASSWORD" | grep Entry | cut -d ',' -f 1
}

export_cert() {
    KEYSTORE=$1
    PASSWORD=$2
    ALIAS=$3
    OUTPUT_FILE=$4

    keytool -keystore $TMPDIR/$KEYSTORE -exportcert -rfc -storepass "$PASSWORD" -alias $ALIAS -file $TMPDIR/$OUTPUT_FILE 2>/dev/null
}

days_to_date() {
    UNTIL_DATE="$1"
    UNTIL_SECONDS=$(date +%s -d "$UNTIL_DATE")
    NOW_SECONDS=$(date +%s)
    INTERVAL_SECONDS=$(($UNTIL_SECONDS - $NOW_SECONDS))
    echo $(($INTERVAL_SECONDS / 86400))
}

list_expirations() {
    KEYSTORE=$1
    PASSWORD=$2
    TITLE=$3

    OLD_IFS=$IFS
    IFS=$(echo -en "\n\b")
    for CERT in $(list_entries $KEYSTORE $PASSWORD); do
        export_cert $KEYSTORE $PASSWORD $CERT ${CERT}_cert.p12
        CERT_VALID_FROM=$(get_valid_from ${CERT}_cert.p12)
        CERT_EXPIRATION=$(get_expiration ${CERT}_cert.p12)
        if [[ $ACTION == "list" ]]; then
            echo "$TITLE $CERT valid from $CERT_VALID_FROM, expiration at $CERT_EXPIRATION" >>$TMPDIR/output
        else
            EXPIRATION_IN=$(days_to_date $CERT_EXPIRATION)
            if (($EXPIRATION_IN <= 60)); then
                echo "$TITLE $CERT expires in $EXPIRATION_IN days" >>$TMPDIR/output
            fi
        fi
    done
    IFS=$OLD_IFS
}

check_certificate() {

    S3_DIR=$1
    S3_FILE=$2
    CERTNAME=$3
    LOCAL_FILE=$4

    if [ -z "$LOCAL_FILE" ]; then
        LOCAL_FILE=$S3_FILE
    fi

    copy_to_tmp $S3_DIR $S3_FILE $LOCAL_FILE
    VALID_FROM=$(get_valid_from $LOCAL_FILE)
    EXPIRATION=$(get_expiration $LOCAL_FILE)

    if [[ $ACTION == 'list' ]]; then
        echo "$CERTNAME certificate valid from $VALID_FROM, expiration at $EXPIRATION" >>$TMPDIR/output
    else
        EXPIRATION_IN=$(days_to_date "$EXPIRATION")
        if ((EXPIRATION_IN <= 60)); then
            echo "$CERTNAME certification expires in $EXPIRATION_IN days" >>$TMPDIR/output
        fi
    fi
}

check_db_certificate() {

    CLIENT_ID=$1
    LOCAL_FILE=$2
    CERTNAME=$3

    if [ -z "$LOCAL_FILE" ]; then
        LOCAL_FILE="db_certificate.pem"
    fi

    dump_cert_from_db_to_tmp $CLIENT_ID $LOCAL_FILE
    VALID_FROM=$(get_valid_from $LOCAL_FILE)
    EXPIRATION=$(get_expiration $LOCAL_FILE)

    if [[ $ACTION == 'list' ]]; then
        echo "$CERTNAME certificate valid from $VALID_FROM, expiration at $EXPIRATION" >>$TMPDIR/output
    else
        EXPIRATION_IN=$(days_to_date "$EXPIRATION")
        if ((EXPIRATION_IN <= 60)); then
            echo "$CERTNAME certification expires in $EXPIRATION_IN days" >>$TMPDIR/output
        fi
    fi
}

check_keystore() {

    S3_DIR=$1
    FILE=$2
    KEYSTORE_PASSWORD=$3
    KEYSTORENAME=$4

    copy_to_tmp $S3_DIR $FILE
    list_expirations $FILE $KEYSTORE_PASSWORD "$KEYSTORENAME"
}

if [[ $# == 0 ]]; then
    echo "Environment must be provided as the first parameter"
    exit 0
fi
ENVIRONMENT=$1

ACTION='list'
OUTPUT='print'


for ARG in "$@"; do
    case $ARG in
    -c)
        ACTION='check'
        ;;
    --check)
        ACTION='check'
        ;;
    -l)
        ACTION='list'
        ;;
    --list)
        ACTION='list'
        ;;
    -p)
        OUTPUT='print'
        ;;
    --print)
        OUTPUT='print'
        ;;
    -n)
        OUTPUT='notify'
        ;;
    --notify)
        OUTPUT='notify'
        ;;
    esac
done

TMPDIR=$(mktemp -d)

# suomi.fi identification does not check certificate expiration in metadata
# check_certificate api-gw saml_suomifi_public_key.pem "suomi.fi identification"

# Keycloak certificate is checked from db
# check_certificate api-gw auth_citizen_public_key.pem "Keycloak Citizen realm"
check_db_certificate evaka-customer keycloak_citizen_certificate.pem "Keycloak Citizen realm"

# Azure AD does not check certification expiration in metadata
# check_certificate api-gw saml_ad_public_key.pem "AD SAML"

if [[ "ENVIRONMENT" == evakaoulu-prod ]]; then
    copy_to_tmp api-gw ouluad-internal-prod.pem
    OULUAD_VALID_FROM=$(get_valid_from ouluad-internal-prod.pem)
    OULUAD_EXPIRATION=$(get_expiration ouluad-internal-prod.pem)
    echo "OULU AD certificate valid from $OULUAD_VALID_FROM, expiration at $OULUAD_EXPIRATION" >>$TMPDIR/output
fi

#TODO check staging env also

# Keycloak certificate is checked from db
# check_certificate api-gw auth_employees_public_key.pem "Keycloak Employee realm" internal_auth_public_key.pem
check_db_certificate evaka keycloak_employee_certificate.pem "Keycloak Employee realm"

if [ -z "$KEYSTORE_PASS" ]; then
    KEYSTORE_PASS=$(get_password /${ENVIRONMENT}/message-service/keystore/password)
fi

check_keystore evaka-srv keystore.p12 $KEYSTORE_PASS "eVaka suomi.fi messages certificate"

if [ -z "$TRUSTSTORE_PASS" ]; then
    TRUSTSTORE_PASS=$(get_password /${ENVIRONMENT}/message-service/truststore/password)
fi

check_keystore evaka-srv truststore.p12 $TRUSTSTORE_PASS "suomi.fi messages public certificate"

if [ -z "$VTJ_KEYSTORE_PASS" ]; then
    VTJ_KEYSTORE_PASS=$(get_password /${ENVIRONMENT}/service/xroad/keystore/password)
fi

check_keystore evaka-srv/vtj keystore.p12 $VTJ_KEYSTORE_PASS "eVaka X-Road certificate"

if [ -z "$VTJ_TRUSTSTORE_PASS" ]; then
    VTJ_TRUSTSTORE_PASS=$(get_password /${ENVIRONMENT}/service/xroad/truststore/password)
fi

check_keystore evaka-srv/vtj truststore.p12 $VTJ_TRUSTSTORE_PASS "X-Road public certificate"

if [ -f $TMPDIR/output ]; then
    if [[ $OUTPUT == "print" ]]; then
        cat $TMPDIR/output
    else
        echo "Expiring certificates in $ENVIRONMENT" >$TMPDIR/output2
        echo >>$TMPDIR/output2
        cat $TMPDIR/output >> $TMPDIR/output2
        aws sns publish --topic-arn $SNS_TOPIC_ARN --message file://$TMPDIR/output2
    fi
fi

rm -rf $TMPDIR
