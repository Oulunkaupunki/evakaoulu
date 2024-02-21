#!/bin/bash

# SPDX-FileCopyrightText: 2021-2023 City of Oulu
#
# SPDX-License-Identifier: LGPL-2.1-or-later

export SSHPASS=$SFTP_PASS

run_query() {
    SQL_FILE=$1
    OUTPUT_FILE=$2
    DATE_ARG=$3

    psql --csv -v date_val="'${DATE_ARG}'" -P csv_fieldsep=';' < $SQL_FILE > $OUTPUT_FILE
}

send_file() {
    FILE=$1

    echo "put $FILE" > sftp_batch.txt
    echo "bye" >> sftp_batch.txt

    sshpass -e sftp -o StrictHostKeyChecking=no -o BatchMode=no -b sftp_batch.txt $SFTP_USER@$SFTP_HOST:$SFTP_PATH
    aws s3 cp $FILE s3://$S3_BUCKET/$FILE
}

send_history_file() {
    FILE=$1

    echo "put $FILE" > sftp_batch.txt
    echo "bye" >> sftp_batch.txt

    sshpass -e sftp -o StrictHostKeyChecking=no -o BatchMode=no -b sftp_batch.txt $SFTP_USER@$SFTP_HOST:$SFTP_PATH/history
    aws s3 cp $FILE s3://$S3_BUCKET/history/$FILE
}

run_query_and_send_file() {
    SQL_FILE=$1
    OUTPUT_FILE=$2
    DATE_ARG=$3

    if [[ "${DATE_ARG}" != "" && "${DATE_ARG}" =~ ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]]; then
        run_query $SQL_FILE $OUTPUT_FILE $DATE_ARG
        send_file $OUTPUT_FILE
    else
        echo "No date provided. Format has to be yyyy-mm-dd"
    fi
}

run_history_batch_and_send_file() {
    SQL_FILE=$1
    FILE_NAME_PREFIX=$2

    if [[ "${HISTORY_FROM_DATE}" != "" && "${HISTORY_FROM_DATE}" =~ ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]]; then
        # Conversion to ISO date is required, because date as environment variable is transformed automatically to YYYY-MM-DD hh:mm:ss +0000 UTC -format
        FROM_DATE=$(date -I -d "$(echo $HISTORY_FROM_DATE | grep -Eo '^[0-9]{4}-[0-9]{2}-[0-9]{2}')") || exit -1
        TO_DATE=$(date +%F)

        echo "Running history batches from ${FROM_DATE} -> ${TO_DATE}"

        DATE_VAL=$FROM_DATE
        while [[ "${DATE_VAL}" < "${TO_DATE}" ]]; do
            OUTPUT_FILE="${FILE_NAME_PREFIX}_history_${DATE_VAL}.csv"

            run_query $SQL_FILE $OUTPUT_FILE $DATE_VAL
            send_history_file $OUTPUT_FILE

            DATE_VAL=$(date -I -d "$DATE_VAL + 1 day") || exit -1
        done
    else
        echo "Invalid value set for environment variable HISTORY_FROM_DATE. Format has to be yyyy-mm-dd"
    fi
}

run_history_bulk_and_send_file() {
    SQL_FILE=$1
    FILE_NAME_PREFIX=$2

    if [[ "${HISTORY_FROM_DATE}" != "" && "${HISTORY_FROM_DATE}" =~ ^[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]]; then
        # Conversion to ISO date is required, because date as environment variable is transformed automatically to YYYY-MM-DD hh:mm:ss +0000 UTC -format
        FROM_DATE=$(date -I -d "$(echo $HISTORY_FROM_DATE | grep -Eo '^[0-9]{4}-[0-9]{2}-[0-9]{2}')") || exit -1
        TO_DATE=$(date +%F)

        echo "Running history bulk between ${FROM_DATE} - ${TO_DATE}"

        DATE_VAL=$FROM_DATE
        if [[ "${DATE_VAL}" < "${TO_DATE}" ]]; then
            OUTPUT_FILE="${FILE_NAME_PREFIX}_history_bulk_${DATE_VAL}.csv"

            run_query $SQL_FILE $OUTPUT_FILE $DATE_VAL
            send_history_file $OUTPUT_FILE
        else
            echo "HISTORY_FROM_DATE has to be before current date (${TO_DATE})"
        fi
    else
        echo "Invalid value set for environment variable HISTORY_FROM_DATE. Format has to be yyyy-mm-dd"
    fi
}

DATE=$(date --iso-8601)

if [[ -v HISTORY_ENABLED && $HISTORY_ENABLED == true && -v HISTORY_FROM_DATE ]]; then
    echo "Running history batches enabled"
    run_history_batch_and_send_file sql/DW-Daily_info.sql daily_info
    run_history_batch_and_send_file sql/DW-Units_and_groups.sql units_and_groups

    echo "Running history bulk"
    run_history_bulk_and_send_file sql/history_bulk/DW-Fee_decisions_bulk.sql fee_decisions
    run_history_bulk_and_send_file sql/history_bulk/DW-Voucher_value_decisions_bulk.sql voucher_value_decisions
    run_history_bulk_and_send_file sql/history_bulk/DW-Assistance_need_decisions_bulk.sql assistance_need_decisions
fi

run_query_and_send_file sql/DW-Daily_info.sql daily_info_$DATE.csv $DATE
run_query_and_send_file sql/DW-Units_and_groups.sql units_and_groups_$DATE.csv $DATE
run_query_and_send_file sql/DW-Daily_units_and_groups_attendance.sql daily_units_and_groups_$DATE.csv $DATE
run_query_and_send_file sql/DW-Fee_decisions.sql fee_decisions_$DATE.csv $DATE
run_query_and_send_file sql/DW-Voucher_value_decisions.sql voucher_value_decisions_$DATE.csv $DATE
run_query_and_send_file sql/DW-Assistance_need_decisions.sql assistance_need_decisions_$DATE.csv $DATE
run_query_and_send_file sql/DW-Daycare_assistances.sql daycare_assistances_$DATE.csv $DATE
run_query_and_send_file sql/DW-Preschool_assistances.sql preschool_assistances_$DATE.csv $DATE
run_query_and_send_file sql/DW-Other_assistance_measures.sql other_assistance_measures_$DATE.csv $DATE
run_query_and_send_file sql/DW-Assistance_actions.sql assistance_actions_$DATE.csv $DATE
run_query_and_send_file sql/DW-Daily_units_occupancy_confirmed.sql daily_units_occupancy_confirmed_$DATE.csv $DATE
run_query_and_send_file sql/DW-Daily_units_occupancy_realized.sql daily_units_occupancy_realized_$DATE.csv $DATE
