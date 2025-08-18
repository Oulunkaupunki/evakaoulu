package fi.ouka.evakaoulu

import fi.espoo.evaka.ScheduledJobsEnv
import fi.espoo.evaka.shared.async.AsyncJobRunner
import fi.espoo.evaka.shared.async.AsyncJobType
import fi.espoo.evaka.shared.async.removeUnclaimedJobs
import fi.espoo.evaka.shared.db.Database
import fi.espoo.evaka.shared.domain.EvakaClock
import fi.espoo.evaka.shared.job.JobSchedule
import fi.espoo.evaka.shared.job.ScheduledJobDefinition
import fi.espoo.evaka.shared.job.ScheduledJobSettings
import fi.ouka.evakaoulu.dw.DWQuery
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalTime

enum class EvakaOuluScheduledJob(
    val fn: (EvakaOuluScheduledJobs, Database.Connection, EvakaClock) -> Unit,
    val defaultSettings: ScheduledJobSettings,
) {
    PlanDWExportJobs(
        { jobs, db, clock -> jobs.planDWJobs(db, clock, DWQuery.entries) },
        ScheduledJobSettings(enabled = false, schedule = JobSchedule.daily(LocalTime.of(20, 0))),
    ),
}

class EvakaOuluScheduledJobs(
    private val asyncJobRunner: AsyncJobRunner<EvakaOuluAsyncJob>,
    env: ScheduledJobsEnv<EvakaOuluScheduledJob>,
) : JobSchedule {
    private val logger = KotlinLogging.logger {}

    override val jobs: List<ScheduledJobDefinition> =
        env.jobs.map {
            ScheduledJobDefinition(it.key, it.value) { db, clock -> it.key.fn(this, db, clock) }
        }

    fun planDWJobs(
        db: Database.Connection,
        clock: EvakaClock,
        selected: List<DWQuery>?,
    ) {
        val queries = selected ?: DWQuery.entries
        logger.info { "Planning DW jobs for ${queries.size} queries" }
        db.transaction { tx ->
            tx.removeUnclaimedJobs(setOf(AsyncJobType(EvakaOuluAsyncJob.SendDWQuery::class)))
            asyncJobRunner.plan(
                tx,
                queries.asSequence().map(EvakaOuluAsyncJob::SendDWQuery),
                runAt = clock.now(),
                retryCount = 1,
            )
        }
    }
}
