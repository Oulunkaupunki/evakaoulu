{
  /*
SPDX-FileCopyrightText: 2021 City of Oulu

SPDX-License-Identifier: LGPL-2.1-or-later
*/
}

import { FeatureFlags } from 'lib-customizations/types'

import { env, Env } from './env'

type Features = {
  default: FeatureFlags
} & {
  [k in Env]: FeatureFlags
}

const features: Features = {
  default: {
    citizenShiftCareAbsence: false,
    citizenContractDayAbsence: true,
    assistanceActionOther: true,
    daycareApplication: {
      dailyTimes: true
    },
    preschoolApplication: {
      connectedDaycarePreferredStartDate: false,
      serviceNeedOption: false
    },
    decisionDraftMultipleUnits: false,
    groupsTableServiceNeeds: true,
    preschool: true,
    urgencyAttachments: true,
    preparatory: true,
    experimental: {
      leops: true,
      citizenVasu: true,
      voucherUnitPayments: true,
      assistanceNeedDecisions: true,
      assistanceNeedDecisionsLanguageSelect: false,
      staffAttendanceTypes: true,
      fosterParents: true
    }
  },
  staging: {
    citizenShiftCareAbsence: false,
    citizenContractDayAbsence: true,
    assistanceActionOther: true,
    daycareApplication: {
      dailyTimes: true
    },
    preschoolApplication: {
      connectedDaycarePreferredStartDate: false,
      serviceNeedOption: false
    },
    decisionDraftMultipleUnits: false,
    groupsTableServiceNeeds: true,
    preschool: true,
    urgencyAttachments: true,
    preparatory: true,
    experimental: {
      leops: true,
      citizenVasu: true,
      voucherUnitPayments: true,
      assistanceNeedDecisions: true,
      assistanceNeedDecisionsLanguageSelect: false,
      staffAttendanceTypes: true,
      fosterParents: true
    }
  },
  prod: {
    citizenShiftCareAbsence: false,
    citizenContractDayAbsence: true,
    assistanceActionOther: true,
    daycareApplication: {
      dailyTimes: true
    },
    preschoolApplication: {
      connectedDaycarePreferredStartDate: false,
      serviceNeedOption: false
    },
    decisionDraftMultipleUnits: false,
    groupsTableServiceNeeds: true,
    preschool: true,
    urgencyAttachments: true,
    preparatory: true,
    experimental: {
      leops: true,
      citizenVasu: true,
      voucherUnitPayments: true,
      assistanceNeedDecisions: true,
      assistanceNeedDecisionsLanguageSelect: false,
      staffAttendanceTypes: true,
      fosterParents: true
    }
  }
}

const featureFlags = features[env()]

export default featureFlags
