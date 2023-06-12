#!/bin/bash

# PRINT SOMETHING for testing (should contain DW report exporting logic)
echo "Hello world!"

DATE=`date +%Y%m%d`
FILE=test_$DATE.txt
touch $FILE

echo "put $FILE" > sftp_batch.txt
echo "bye" >> sftp_batch.txt

export SSHPASS=$SFTP_PASS
sshpass -e sftp -o StrictHostKeyChecking=no -o BatchMode=no -b sftp_batch.txt $SFTP_USER@$SFTP_HOST:$SFTP_PATH
