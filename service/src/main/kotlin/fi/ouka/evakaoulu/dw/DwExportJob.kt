package fi.ouka.evakaoulu.dw

import fi.espoo.evaka.espoo.bi.CSV_CHARSET
import fi.espoo.evaka.espoo.bi.EspooBiJob
import fi.espoo.evaka.shared.db.Database
import fi.espoo.evaka.shared.domain.EvakaClock
import fi.ouka.evakaoulu.EvakaOuluAsyncJob
import java.time.Duration

class DwExportJob(
    private val client: DwExportClient,
) {
    fun sendDwQuery(
        db: Database.Connection,
        clock: EvakaClock,
        msg: EvakaOuluAsyncJob.SendDWQuery,
    ) = sendDwQuery(db, clock, msg.query.queryName, msg.query.query)

    fun sendDwQuery(
        db: Database.Connection,
        clock: EvakaClock,
        queryName: String,
        query: DwQueries.CsvQuery,
    ) {
        db.read { tx ->
            tx.setStatementTimeout(Duration.ofMinutes(10))

            query(tx) { records ->
                val stream = EspooBiJob.CsvInputStream(CSV_CHARSET, records)
                client.sendDwCsvFile(queryName, clock, stream)
            }
        }
    }
}
