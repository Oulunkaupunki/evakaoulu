# SPDX-FileCopyrightText: 2021 City of Oulu
#
# SPDX-License-Identifier: LGPL-2.1-or-later

FROM node:16.13 AS theme
COPY theme/package*.json ./
RUN npm ci
COPY theme .
COPY theme-fi.sed ./
COPY OuluLogo.png evaka/login/resources/img/EspooLogo.png
RUN sed -i -E -f theme-fi.sed evaka/login/messages/messages_fi.properties && \
    sed -i -e '/<#--/d' -e '/-->/d' evaka/login/login-update-profile.ftl
RUN npm run build

FROM quay.io/keycloak/keycloak:16.1.0
COPY --from=theme evaka /opt/jboss/keycloak/themes/evaka