#!/bin/bash

# dependencies: AWS CLI, keytool, jq, psql

copy_to_tmp() {

    S3_DIR=$1
    FILE=$2
    TARGET=$3

    if [ -z "$TARGET" ]; then TARGET=$FILE; fi

    aws s3 cp s3://${ENVIRONMENT}-deployment/$S3_DIR/$FILE $TMPDIR/$TARGET >/dev/null
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

if [[ "ENVIRONMENT" == evakaoulu-prod ]]; then
    copy_to_tmp api-gw ouluad-internal-prod.pem
    OULUAD_VALID_FROM=$(get_valid_from ouluad-internal-prod.pem)
    OULUAD_EXPIRATION=$(get_expiration ouluad-internal-prod.pem)
    echo "OULU AD certificate valid from $OULUAD_VALID_FROM, expiration at $OULUAD_EXPIRATION" >>$TMPDIR/output
fi

#TODO check staging env also

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
