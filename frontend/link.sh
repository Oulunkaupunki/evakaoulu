#!/bin/bash

# SPDX-FileCopyrightText: 2021 City of Oulu
#
# SPDX-License-Identifier: LGPL-2.1-or-later

# Example how to link these customizations to evaka build
set -euo pipefail

EVAKA=../evaka/frontend
CUSTOMIZATIONS="${EVAKA}"/src/lib-customizations/oulu

if [ ! -e "${CUSTOMIZATIONS}" ]; then
  ln -v -s $(readlink -f ./oulu) "${CUSTOMIZATIONS}"
else
  echo "${CUSTOMIZATIONS}" already exists: no linking was done
fi
