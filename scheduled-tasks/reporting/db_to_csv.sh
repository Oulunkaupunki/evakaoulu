#!/bin/bash

export SSHPASS=$SFTP_PASS
export PGHOST=evakaoulu-dev.cp2n7ceiwm0p.eu-west-1.rds.amazonaws.com

run_query() {
    SQL_FILE=$1
    OUTPUT_FILE=$2

    psql --csv < $SQL_FILE > $OUTPUT_FILE
}

send_file() {
    FILE=$1

    echo "put $FILE" > sftp_batch.txt
    echo "bye" >> sftp_batch.txt

    sshpass -e sftp -o StrictHostKeyChecking=no -o BatchMode=no -b sftp_batch.txt $SFTP_USER@$SFTP_HOST:$SFTP_PATH
    aws s3 cp $FILE s3://$S3_BUCKET/$FILE
}

run_query_and_send_file() {
    SQL_FILE=$1
    OUTPUT_FILE=$2

    run_query $SQL_FILE $OUTPUT_FILE
    send_file $OUTPUT_FILE
}

DATE=$(date --iso-8601)

run_query_and_send_file sql/DW-Daily_info.sql daily_info_$DATE.csv
run_query_and_send_file sql/DW-Units_and_groups.sql units_and_groups_$DATE.csv
run_query_and_send_file sql/DW-Fee_decisions.sql fee_decisions_$DATE.csv
run_query_and_send_file sql/DW-Voucher_value_decisions.sql voucher_value_decisions_$DATE.csv
run_query_and_send_file sql/DW-Assistance_need_decisions.sql assistance_need_decisions_$DATE.csv
