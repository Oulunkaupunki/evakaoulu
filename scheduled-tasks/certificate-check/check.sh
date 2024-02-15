#!/bin/bash

# dependencies: AWS CLI, keytool, jq

copy_to_tmp() {

    S3_DIR=$1
    FILE=$2
    TARGET=$3

    if [ -z "$TARGET" ]; then TARGET=$FILE; fi

    aws s3 cp s3://${ENVIRONMENT}-deployment/$S3_DIR/$FILE $TMPDIR/$TARGET >/dev/null
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

# Cert expiration is not needed in SAML cases -- AD check is needed
#check_certificate enduser-gw saml_public_key.pem "suomi.fi identification"
#check_certificate enduser-gw auth_public_key.pem "Keycloak Citizen realm"
check_certificate internal-gw saml_public_key.pem "AD SAML"

if [[ "ENVIRONMENT" == evakaturku-prod ]]; then
    copy_to_tmp internal-gw turkuad-internal-prod.pem
    TURKUAD_VALID_FROM=$(get_valid_from turkuad-internal-prod.pem)
    TURKUAD_EXPIRATION=$(get_expiration turkuad-internal-prod.pem)
    echo "TURKU AD certificate valid from $TURKUAD_VALID_FROM, expiration at $TURKUAD_EXPIRATION" >>$TMPDIR/output
fi

check_certificate internal-gw auth_public_key.pem "Keycloak Employee realm" internal_auth_public_key.pem

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
        cat $TMPDIR/output >>$TMPDIR/output2
        aws sns publish --topic-arn $SNS_TOPIC_ARN --message file://$TMPDIR/output2
    fi
fi

rm -rf $TMPDIR
