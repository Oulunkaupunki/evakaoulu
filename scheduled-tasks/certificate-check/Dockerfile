# SPDX-FileCopyrightText: 2021-2023 City of Oulu
#
# SPDX-License-Identifier: LGPL-2.1-or-later

FROM alpine:3.23.3
ARG CACHE_BUST
# openjdk8-jre-base is needed because it contains keytool
RUN apk add --no-cache bash coreutils aws-cli jq openssl openjdk8-jre-base postgresql16-client
ADD check.sh check.sh
ADD sql sql
CMD ./check.sh $ENVIRONMENT --check --notify
