package fi.ouka.evakaoulu.dw

import fi.espoo.evaka.absence.AbsenceCategory
import fi.espoo.evaka.pis.getEmployee
import fi.espoo.evaka.shared.AreaId
import fi.espoo.evaka.shared.EvakaUserId
import fi.espoo.evaka.shared.ServiceNeedOptionId
import fi.espoo.evaka.shared.db.QuerySql
import fi.espoo.evaka.shared.dev.DevAbsence
import fi.espoo.evaka.shared.dev.DevDaycare
import fi.espoo.evaka.shared.dev.DevDaycareGroup
import fi.espoo.evaka.shared.dev.DevEmployee
import fi.espoo.evaka.shared.dev.DevPerson
import fi.espoo.evaka.shared.dev.DevPersonType
import fi.espoo.evaka.shared.dev.DevPlacement
import fi.espoo.evaka.shared.dev.insert
import fi.espoo.evaka.shared.domain.HelsinkiDateTime
import fi.espoo.evaka.shared.domain.MockEvakaClock
import fi.ouka.evakaoulu.AbstractIntegrationTest
import fi.ouka.evakaoulu.invoice.service.SftpSender
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.springframework.beans.factory.annotation.Autowired
import software.amazon.awssdk.services.s3.S3AsyncClient
import java.time.LocalDate
import java.time.LocalTime

class DWExportJobTest : AbstractIntegrationTest() {
    private val clock = MockEvakaClock(HelsinkiDateTime.of(LocalDate.of(2019, 7, 15), LocalTime.of(23, 0)))

    @Autowired
    private lateinit var s3AsyncClient: S3AsyncClient

    @Autowired
    private lateinit var sftpSender: SftpSender

    private lateinit var job: DWExportJob

    @BeforeAll
    fun beforeAll() {
        val exportClient = FileDWExportClient(s3AsyncClient, sftpSender, properties)
        job = DWExportJob(exportClient)
    }

    @BeforeEach
    fun beforeEach() {
        insertCriticalTestData()
    }

    @TestFactory
    fun testDWExports() =
        DWQuery.entries.map {
            DynamicTest.dynamicTest("Test '${it.queryName}' export") { sendAndAssertDWQueryCsv(it) }
        }

    private fun sendAndAssertDWQueryCsv(query: DWQuery) {
        job.sendDWQuery(db, clock, query.queryName, query.query)
    }

    private fun insertCriticalTestData() {
        db.transaction { tx ->
            val employeeId = tx.insert(DevEmployee())
            val areaId =
                tx
                    .createQuery(
                        QuerySql { sql("select id from care_area order by short_name limit 1") },
                    ).exactlyOne<AreaId>()
            val snoId =
                tx
                    .createQuery(
                        QuerySql { sql("select id from service_need_option order by name_fi limit 1") },
                    ).exactlyOne<ServiceNeedOptionId>()
            val daycareId = tx.insert(DevDaycare(areaId = areaId))
            tx.insert(DevDaycareGroup(daycareId = daycareId))
            val childId = tx.insert(DevPerson(), DevPersonType.CHILD)
            val guardianId = tx.insert(DevPerson(), DevPersonType.RAW_ROW)
            tx.insert(
                DevPlacement(
                    childId = childId,
                    unitId = daycareId,
                    createdBy = EvakaUserId(employeeId.raw),
                    modifiedBy = EvakaUserId(employeeId.raw),
                ),
            )

            tx.insert(
                DevAbsence(
                    childId = childId,
                    date = LocalDate.of(2019, 7, 15),
                    modifiedBy = EvakaUserId(employeeId.raw),
                    absenceCategory = AbsenceCategory.BILLABLE,
                ),
            )
        }
    }
}
