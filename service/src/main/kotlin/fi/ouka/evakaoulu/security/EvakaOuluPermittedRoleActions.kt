package fi.ouka.evakaoulu.security

import fi.espoo.evaka.shared.security.PermittedRoleActions

class EvakaOuluPermittedRoleActions(private val defaults: PermittedRoleActions) : PermittedRoleActions by defaults {
}
