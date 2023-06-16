#!/bin/bash

export SSHPASS=$SFTP_PASS

send_file() {
    FILE=$1

    echo "put $FILE" > sftp_batch.txt
    echo "bye" >> sftp_batch.txt

    sshpass -e sftp -o StrictHostKeyChecking=no -o BatchMode=no -b sftp_batch.txt "$SFTP_USER"@"$SFTP_HOST":"$SFTP_PATH"
    aws s3 cp "$FILE" s3://"$S3_BUCKET"/"$FILE"
}

DATE=$(date --iso-8601)

# Daily info

DAILY_QUERY=$(cat sql/DW-Daily-info.sql)
DAILY_FILE=Daily_info_$DATE.csv
psql -c "COPY $DAILY_QUERY TO STDOUT WITH DELIMITER ',' CSV HEADER;" > "$DAILY_FILE"
send_file "$DAILY_FILE"

# Assistance need decisions

ASSISTANCE_QUERY=$(cat sql/DW-Assistance_need_decisions.sql)
ASSISTANCE_FILE=Daily_info_$DATE.csv
psql -c "COPY $ASSISTANCE_QUERY TO STDOUT WITH DELIMITER ',' CSV HEADER;" > "$ASSISTANCE_FILE"
send_file "$ASSISTANCE_FILE"

# Units and groups

UNIT_QUERY=$(cat sql/DW-Unite_and_groups.sql)
UNIT_FILE=Daily_info_$DATE.csv
psql -c "COPY $UNIT_QUERY TO STDOUT WITH DELIMITER ',' CSV HEADER;" > "$UNIT_FILE"
send_file "$UNIT_FILE"

# Fee decisions

FEE_QUERY=$(cat sql/DW-Fee_decisions.sql)
FEE_FILE=Daily_info_$DATE.csv
psql -c "COPY $FEE_QUERY TO STDOUT WITH DELIMITER ',' CSV HEADER;" > "$FEE_FILE"
send_file "$FEE_FILE"

# Voucher value decisions

VOUCHER_QUERY=$(cat sql/DW-Voucher_value_decisions.sql)
VOUCHER_FILE=Daily_info_$DATE.csv
psql -c "COPY $VOUCHER_QUERY TO STDOUT WITH DELIMITER ',' CSV HEADER;" > "$VOUCHER_FILE"
send_file "$VOUCHER_FILE"
