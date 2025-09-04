package fi.ouka.evakaoulu

import fi.espoo.evaka.shared.async.AsyncJobPayload
import fi.espoo.evaka.shared.async.AsyncJobPool
import fi.espoo.evaka.shared.async.AsyncJobRunner
import fi.espoo.evaka.shared.auth.AuthenticatedUser
import fi.ouka.evakaoulu.dw.DwExportJob
import fi.ouka.evakaoulu.dw.DwQuery

sealed interface EvakaOuluAsyncJob : AsyncJobPayload {
    data class SendDWQuery(
        val query: DwQuery,
    ) : EvakaOuluAsyncJob {
        override val user: AuthenticatedUser? = null
    }

    companion object {
        val pool =
            AsyncJobRunner.Pool(
                AsyncJobPool.Id(EvakaOuluAsyncJob::class, "oulu"),
                AsyncJobPool.Config(concurrency = 1),
                setOf(SendDWQuery::class),
            )
    }
}

class EvakaOuluAsyncJobRegistration(
    runner: AsyncJobRunner<EvakaOuluAsyncJob>,
    dwExportJob: DwExportJob,
) {
    init {
        dwExportJob.let { runner.registerHandler(it::sendDwQuery) }
    }
}
