# SPDX-FileCopyrightText: 2021-2022 City of Turku
#
# SPDX-License-Identifier: LGPL-2.1-or-later

FROM ubuntu:24.04

ARG CACHE_BUST
ARG DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
    && apt-get -y install curl lsb-release gnupg sshpass zip unzip \
    && curl https://www.postgresql.org/media/keys/ACCC4CF8.asc | gpg --dearmor | tee /etc/apt/trusted.gpg.d/apt.postgresql.org.gpg > /dev/null \
    && echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list \
    && apt-get update \
    && apt-get -y install postgresql-client-16 \
    && rm -rf /var/lib/apt/lists/* \
    && mkdir awscli \
    && cd awscli \
    && curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" \
    && unzip awscliv2.zip \
    && ./aws/install \
    && cd .. \
    && rm -rf awscli
ADD db_to_csv.sh db_to_csv.sh
ADD sql sql
CMD ["./db_to_csv.sh"]
