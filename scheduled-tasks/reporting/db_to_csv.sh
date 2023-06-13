#!/bin/bash

export SSHPASS=$SFTP_PASS

send_file() {
    FILE=$1

    echo "put $FILE" > sftp_batch.txt
    echo "bye" >> sftp_batch.txt

    sshpass -e sftp -o StrictHostKeyChecking=no -o BatchMode=no -b sftp_batch.txt $SFTP_USER@$SFTP_HOST:$SFTP_PATH
    aws s3 cp $FILE s3://$S3_BUCKET/$FILE
}

# PRINT SOMETHING for testing (should contain DW report exporting logic)
echo "Hello world!"

DATE=`date +%Y%m%d`
FILE=test_$DATE.txt
touch $FILE

send_file $FILE
