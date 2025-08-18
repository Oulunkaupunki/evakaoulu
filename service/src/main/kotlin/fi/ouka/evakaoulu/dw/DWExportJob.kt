package fi.ouka.evakaoulu.dw

import fi.espoo.evaka.espoo.bi.CSV_CHARSET
import fi.espoo.evaka.espoo.bi.EspooBiJob
import fi.espoo.evaka.shared.db.Database
import fi.espoo.evaka.shared.domain.EvakaClock
import fi.ouka.evakaoulu.EvakaOuluAsyncJob
import java.time.Duration

class DWExportJob(private val client: DWExportClient) {
    fun sendDWQuery(
        db: Database.Connection,
        clock: EvakaClock,
        msg: EvakaOuluAsyncJob.SendDWQuery,
    ) = sendDWQuery(db, clock, msg.query.queryName, msg.query.query)

    fun sendDWQuery(
        db: Database.Connection,
        clock: EvakaClock,
        queryName: String,
        query: DWQueries.CsvQuery,
    ) {
        db.read { tx ->
            tx.setStatementTimeout(Duration.ofMinutes(10))

            query(tx) { records ->
                val stream = EspooBiJob.CsvInputStream(CSV_CHARSET, records)
                client.sendDWCsvFile(queryName, clock, stream)
            }
        }
    }
}
