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
    assistanceActionOther: true,
    financeDecisionHandlerSelect: false,
    feeDecisionPreschoolClubFilter: true,
    daycareApplication: {
      dailyTimes: true
    },
    preschoolApplication: {
      connectedDaycarePreferredStartDate: false,
      serviceNeedOption: false
    },
    decisionDraftMultipleUnits: false,
    preschool: true,
    urgencyAttachments: true,
    preparatory: true,
    childDiscussion: true,
    placementGuarantee: false,
    voucherUnitPayments: true,
    assistanceNeedDecisionsLanguageSelect: false,
    staffAttendanceTypes: true,
    childDocuments: true,
    intermittentShiftCare: false,
    assistanceNeedPreschoolDecisions: true,
    feeDecisionIgnoredStatus: true,
    hojks: false,
    personDuplicate: false,
    citizenAttendanceSummary: false,
    noAbsenceType: false
  },
  staging: {
    citizenShiftCareAbsence: false,
    assistanceActionOther: true,
    daycareApplication: {
      dailyTimes: true
    },
    preschoolApplication: {
      connectedDaycarePreferredStartDate: false,
      serviceNeedOption: false
    },
    decisionDraftMultipleUnits: false,
    preschool: true,
    urgencyAttachments: true,
    preparatory: true,
    financeDecisionHandlerSelect: false,
    feeDecisionPreschoolClubFilter: true,
    childDiscussion: true,
    placementGuarantee: false,
    voucherUnitPayments: true,
    assistanceNeedDecisionsLanguageSelect: false,
    staffAttendanceTypes: true,
    childDocuments: true,
    intermittentShiftCare: false,
    assistanceNeedPreschoolDecisions: true,
    feeDecisionIgnoredStatus: true,
    hojks: false,
    personDuplicate: false,
    citizenAttendanceSummary: false,
    noAbsenceType: false
  },
  prod: {
    citizenShiftCareAbsence: false,
    assistanceActionOther: true,
    daycareApplication: {
      dailyTimes: true
    },
    preschoolApplication: {
      connectedDaycarePreferredStartDate: false,
      serviceNeedOption: false
    },
    decisionDraftMultipleUnits: false,
    preschool: true,
    urgencyAttachments: true,
    preparatory: true,
    financeDecisionHandlerSelect: false,
    feeDecisionPreschoolClubFilter: true,
    childDiscussion: true,
    placementGuarantee: false,
    voucherUnitPayments: true,
    assistanceNeedDecisionsLanguageSelect: false,
    staffAttendanceTypes: true,
    childDocuments: true,
    intermittentShiftCare: false,
    assistanceNeedPreschoolDecisions: true,
    feeDecisionIgnoredStatus: true,
    hojks: false,
    personDuplicate: false,
    citizenAttendanceSummary: false,
    noAbsenceType: false
  }
}

const featureFlags = features[env()]

export default featureFlags
