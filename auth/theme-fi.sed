# SPDX-FileCopyrightText: 2021 City of Oulu
#
# SPDX-License-Identifier: LGPL-2.1-or-later

/^(espooLogoAlt[[:blank:]]*=[[:blank:]]*).*/{
s//\1Oulun kaupungin logo/g
w /dev/stdout
}
/^(doAcknowledgeResourcesPrivacyPolicyLink[[:blank:]]*=[[:blank:]]*).*/{
s//\1https:\/\/www.ouka.fi\/oulu\/verkkoasiointi\/tietosuoja/g
w /dev/stdout
}
/^(doAcknowledgeResourcesyDataProtectionLink[[:blank:]]*=[[:blank:]]*).*/{
s//\1https:\/\/www.ouka.fi\/oulu\/verkkoasiointi\/tietosuoja/g
w /dev/stdout
}
/^(doGiveFeedbackLink[[:blank:]]*=[[:blank:]]*).*/{
s//\1https:\/\/www.ouka.fi\/palaute/g
w /dev/stdout
}
