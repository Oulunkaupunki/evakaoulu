# syntax=docker/dockerfile:1.5.2-labs
# SPDX-FileCopyrightText: 2021 City of Oulu
#
# SPDX-License-Identifier: LGPL-2.1-or-later

FROM eclipse-temurin:21-jre-jammy

LABEL maintainer="https://github.com/Oulunkaupunki"

ARG CACHE_BUST

# Use bash instead of dash
SHELL ["/bin/bash", "-c"]

# Install required packages and update time zone information
ENV LC_ALL C.UTF-8
ENV LANG C.UTF-8
ENV LANGUAGE C.UTF-8
RUN set -euxo pipefail \
    && apt-get update \
    && apt-get -y dist-upgrade \
    && DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
        tzdata \
        ca-certificates \
        curl \
        jq \
    && ln -fs /usr/share/zoneinfo/Europe/Helsinki /etc/localtime \
    && dpkg-reconfigure --frontend noninteractive tzdata \
    && rm -rf /var/lib/apt/lists/*

# Set evaka username
ENV USERNAME evaka
ENV HOME_DIR /home/${USERNAME}
ENV USER_ID 1000

# Create a new user named evaka which should be used to run any software.
RUN adduser ${USERNAME} --gecos "" -q --home ${HOME_DIR} --uid ${USER_ID} --disabled-password

# Used for downloading keys from S3
ADD --chown=evaka:evaka --chmod=0700 \
    --checksum=sha256:520ea232e83a7cefe2a87d4f2af8433e383a4351464e213b7dd3b78ca0dc200f \
    https://github.com/espoon-voltti/s3-downloader/releases/download/v1.4.1/s3downloader-linux-amd64 \
    /usr/local/bin/s3download

ADD --chmod=0644 \
    --checksum=sha256:424a3e03a17df0a2bc2b3ca749d81b04e79d59cb7aeec8876a5a3f308d0caf51 \
    https://raw.githubusercontent.com/danielmiessler/SecLists/2023.1/Passwords/xato-net-10-million-passwords-1000000.txt \
    /opt/password-blacklists/xato-net-10-million-passwords-1000000.txt

ADD --chmod=0644 \
    --checksum=sha256:776b76cfe05611cdc3d38c4b35fc92f563fcfd6e3df0e28c524da1d279d44223 \
    https://github.com/DataDog/dd-trace-java/releases/download/v1.31.0/dd-java-agent.jar \
    /opt/dd-java-agent.jar

ADD ./dd-jmxfetch/ /etc/jmxfetch/

USER evaka
WORKDIR /home/evaka

COPY --chown=evaka:evaka "entrypoint.sh" "entrypoint.sh"
COPY --chown=evaka:evaka "target/dependencies/" "./"
COPY --chown=evaka:evaka "target/spring-boot-loader/" "./"
COPY --chown=evaka:evaka "target/snapshot-dependencies/" "./"
COPY --chown=evaka:evaka "target/application/" "./"

# Add build and commit environment variables and labels
# for tracing the image to the commit and build from which the image has been built.
ARG build=none
ARG commit=none
ENV APP_BUILD "$build"
ENV APP_COMMIT "$commit"
LABEL fi.ouka.build="$build" \
      fi.ouka.commit="$commit"

EXPOSE 8888

ENTRYPOINT ["./entrypoint.sh"]
