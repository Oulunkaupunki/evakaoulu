# SPDX-FileCopyrightText: 2021-2023 City of Oulu
#
# SPDX-License-Identifier: LGPL-2.1-or-later

FROM alpine:3.18.5
# openjdk8-jre-base is needed because it contains keytool
RUN apk add --no-cache bash coreutils aws-cli jq openssl openjdk8-jre-base postgresql16-client
ADD check.sh check.sh
CMD ./check.sh $ENVIRONMENT --check --notify
