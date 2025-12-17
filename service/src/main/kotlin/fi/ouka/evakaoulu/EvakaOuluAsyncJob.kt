package fi.ouka.evakaoulu

import fi.espoo.evaka.shared.async.AsyncJobPayload
import fi.espoo.evaka.shared.async.AsyncJobPool
import fi.espoo.evaka.shared.async.AsyncJobRunner
import fi.espoo.evaka.shared.auth.AuthenticatedUser
import fi.ouka.evakaoulu.dw.DwExportJob
import fi.ouka.evakaoulu.dw.DwQuery
import fi.ouka.evakaoulu.dw.FabricHistoryQuery
import fi.ouka.evakaoulu.dw.FabricQuery

sealed interface EvakaOuluAsyncJob : AsyncJobPayload {
    data class SendDWQuery(
        val query: DwQuery,
    ) : EvakaOuluAsyncJob {
        override val user: AuthenticatedUser? = null
    }

    data class SendFabricQuery(
        val query: FabricQuery,
    ) : EvakaOuluAsyncJob {
        override val user: AuthenticatedUser? = null
    }

    data class SendFabricHistoryQuery(
        val query: FabricHistoryQuery,
    ) : EvakaOuluAsyncJob {
        override val user: AuthenticatedUser? = null
    }

    companion object {
        val pool =
            AsyncJobRunner.Pool(
                AsyncJobPool.Id(EvakaOuluAsyncJob::class, "oulu"),
                AsyncJobPool.Config(concurrency = 1),
                setOf(SendDWQuery::class, SendFabricQuery::class, SendFabricHistoryQuery::class),
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
