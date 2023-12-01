package fi.ouka.evakaoulu.security

import fi.espoo.evaka.shared.auth.UserRole
import fi.espoo.evaka.shared.security.Action
import fi.espoo.evaka.shared.security.actionrule.ActionRuleMapping
import fi.espoo.evaka.shared.security.actionrule.HasGlobalRole
import fi.espoo.evaka.shared.security.actionrule.HasUnitRole
import fi.espoo.evaka.shared.security.actionrule.ScopedActionRule
import fi.espoo.evaka.shared.security.actionrule.UnscopedActionRule

class EvakaOuluActionRuleMapping : ActionRuleMapping {
    override fun rulesOf(action: Action.UnscopedAction): Sequence<UnscopedActionRule> = when (action) {
        Action.Global.SUBMIT_PATU_REPORT,
        Action.Global.SEND_PATU_REPORT -> sequenceOf()
        Action.Global.SETTINGS_PAGE,
        Action.Global.UPDATE_SETTINGS -> action.defaultRules.asSequence() + sequenceOf(HasGlobalRole(UserRole.SERVICE_WORKER))
        else -> action.defaultRules.asSequence()
    }

    override fun <T> rulesOf(action: Action.ScopedAction<in T>): Sequence<ScopedActionRule<in T>> = when (action) {
        Action.BackupCare.UPDATE,
        Action.BackupCare.DELETE -> {
            @Suppress("UNCHECKED_CAST")
            action.defaultRules.asSequence() + sequenceOf(
                HasUnitRole(UserRole.STAFF).inPlacementUnitOfChildOfBackupCare() as ScopedActionRule<in T>
            )
        }
        Action.Child.READ_VASU_DOCUMENT,
        Action.Child.CREATE_BACKUP_CARE,
        Action.Child.READ_ASSISTANCE_ACTION -> {
            @Suppress("UNCHECKED_CAST")
            action.defaultRules.asSequence() + sequenceOf(
                HasUnitRole(UserRole.STAFF).inPlacementUnitOfChild() as ScopedActionRule<in T>
            )
        }
        Action.Person.UPDATE_FROM_VTJ -> {
            @Suppress("UNCHECKED_CAST")
            action.defaultRules.asSequence() + sequenceOf(
                HasGlobalRole(UserRole.FINANCE_ADMIN) as ScopedActionRule<in T>
            )
        }
        Action.Person.READ_FAMILY_OVERVIEW -> {
            @Suppress("UNCHECKED_CAST")
            action.defaultRules.asSequence() + sequenceOf(
                HasGlobalRole(UserRole.SERVICE_WORKER) as ScopedActionRule<in T>
            )
        }
        else -> action.defaultRules.asSequence()
    }
}
