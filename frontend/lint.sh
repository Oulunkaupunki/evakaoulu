#!/bin/bash

# SPDX-FileCopyrightText: 2025 City of Turku
#
# SPDX-License-Identifier: LGPL-2.1-or-later

set -euo pipefail

cd "$(dirname "$0")/../evaka/frontend"; \
yarn eslint --max-warnings 0 src/lib-customizations/oulu/.
yarn tsc --build --force src/lib-customizations/
