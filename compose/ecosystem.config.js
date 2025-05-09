// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

const path = require('path')

const defaults = {
  autorestart: false
}

module.exports = {
  apps: [{
    name: 'apigw',
    script: 'yarn && yarn clean && yarn dev',
    cwd: path.resolve(__dirname, '../evaka/apigw'),
    env: {
      AD_SAML_EXTERNAL_ID_PREFIX: 'oulu-ad'
    },
    ...defaults
  }, {
    name: 'frontend',
    script: 'yarn && yarn clean && EVAKA_CUSTOMIZATIONS=oulu yarn dev',
    cwd: path.resolve(__dirname, '../evaka/frontend'),
    env: {
      'ICONS': process.env.ICONS
    },
    ...defaults
  }, {
    name: 'service',
    script: `${__dirname}/../evaka/compose/run-after-db.sh`,
    args: './gradlew --no-daemon bootRun',
    cwd: path.resolve(__dirname, '../service'),
    ...defaults
  },
  ],
}
