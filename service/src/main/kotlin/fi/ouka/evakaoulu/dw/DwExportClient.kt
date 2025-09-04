package fi.ouka.evakaoulu.dw

import fi.espoo.evaka.espoo.bi.EspooBiJob
import fi.espoo.evaka.shared.domain.EvakaClock

interface DwExportClient {
    fun sendDwCsvFile(
        queryName: String,
        clock: EvakaClock,
        stream: EspooBiJob.CsvInputStream,
    ): Pair<String, String>
}
