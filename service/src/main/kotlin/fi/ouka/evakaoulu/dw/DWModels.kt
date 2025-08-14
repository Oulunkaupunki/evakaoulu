package fi.ouka.evakaoulu.dw

import fi.espoo.evaka.absence.AbsenceCategory
import fi.espoo.evaka.absence.AbsenceType
import fi.espoo.evaka.application.ApplicationOrigin
import fi.espoo.evaka.application.ApplicationStatus
import fi.espoo.evaka.application.ApplicationType
import fi.espoo.evaka.assistance.DaycareAssistanceLevel
import fi.espoo.evaka.assistance.OtherAssistanceMeasureType
import fi.espoo.evaka.assistance.PreschoolAssistanceLevel
import fi.espoo.evaka.assistanceneed.decision.AssistanceNeedDecisionStatus
import fi.espoo.evaka.daycare.CareType
import fi.espoo.evaka.daycare.domain.ProviderType
import fi.espoo.evaka.invoicing.domain.FeeDecisionStatus
import fi.espoo.evaka.invoicing.domain.FeeDecisionType
import fi.espoo.evaka.invoicing.domain.VoucherValueDecisionStatus
import fi.espoo.evaka.invoicing.domain.VoucherValueDecisionType
import fi.espoo.evaka.placement.PlacementType
import fi.espoo.evaka.serviceneed.ShiftCareType
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class DWAbsence(
    val lapsenid: UUID,
    val poissaolonpvm: LocalDate,
    val poissaolontyyppi: AbsenceType,
    val poissaolonkategoria: AbsenceCategory,
    val sijoitustyyppi: PlacementType,
)

data class DWApplicationInfo(
    val hakemuksenId: UUID,
    val hakemusLuotu: LocalDateTime,
    val hakemustaPaivitetty: LocalDateTime,
    val tyyppi: ApplicationType,
    val tilanne: ApplicationStatus,
    val alkupera: ApplicationOrigin,
    val siirtohakemus: Boolean,
    val lapsenId: UUID,
    val syntymaaika: LocalDate,
    val yksikot: String,
    val haluttuAloituspaiva: LocalDate?,
    val yksikkoNimi: String,
    val alueId: UUID,
    val alueNimi: String,
)

data class DWAssistanceAction(
    val pvm: LocalDate,
    val lapsenId: UUID,
    val tukitoimi: String?,
    val muuTukitoimi: String,
    val aloitusPvm: LocalDate,
    val loppuPvm: LocalDate,
)

data class DWAssistanceNeedDecision(
    val aikaleima: LocalDateTime,
    val päätosTuesta: Int,
    val lapsenId: UUID,
    val tuenAlkupvm: String,
    val tuenLoppupvm: LocalDate,
    val pienennettyRyhmä: Boolean,
    val erityisryhmä: Boolean,
    val pienryhmä: Boolean,
    val ryhmäkohtainenAvustaja: Boolean,
    val lapsikohtainenAvustaja: Boolean,
    val henkilöresurssienLisäys: Boolean,
    val veonAntamaKonsultaatio: Boolean,
    val veonOsaAikainenOpetus: Boolean,
    val veonKokoaikainenOpetus: Boolean,
    val tulkitsemisJaAvustamispalvelut: Boolean,
    val apuvälineet: Boolean,
    val tuenTaso: List<String>,
    val tila: AssistanceNeedDecisionStatus,
)

data class DWDailyInfo(
    val pvm: LocalDate,
    val lapsenId: UUID,
    val henkilöturvatunnus: String?,
    val syntymäaika: LocalDate,
    val kieli: String?,
    val postiosoite: String,
    val postinumero: String,
    val postitoimipaikka: String,
    val kansalaisuudet: List<String>,
    val sijoitustyyppi: PlacementType,
    val sijoitusyksikköId: UUID,
    val yksikönNimi: String,
    val palvelualueId: UUID,
    val palvelualue: String,
    val toimintamuoto: List<CareType>,
    val järjestämistapa: ProviderType,
    val kustannuspaikka: String?,
    val sijoitusryhmäId: UUID,
    val sijoitusryhmä: String,
    val varahoitoyksikköId: UUID?,
    val varahoitoyksikkö: String?,
    val varahoitoryhmäId: UUID?,
    val varahoitoryhmä: String?,
    val palveluntarveMerkitty: Boolean?,
    val palveluntarve: String?,
    val palveluntarveId: UUID?,
    val osapäiväinen: Boolean?,
    val osaviikkoinen: Boolean?,
    val vuorohoito: ShiftCareType?,
    val tuntejaViikossa: Int?,
    val tuentarpeenKerroin: Double?,
    val lapsenKapasiteetti: Double?,
    val poissaolonSyy: List<AbsenceType>,
)

data class DWDailyUnitsAndGroupsAttendance(
    val aikaleima: LocalDateTime,
    val pvm: LocalDate?,
    val toimintayksikkö: String,
    val toimintayksikköId: UUID,
    val toimintapäivät: List<Int>,
    val vuorohoitoyksikkö: Boolean?,
    val vuorohoitopäivät: List<Int>,
    val vuorohoitopyhäpäivinä: Boolean,
    val ryhmä: String,
    val ryhmäId: UUID,
    val toimintayksikönLaskennallinenLapsimäärä: Int,
    val toimintayksikönLapsimäärä: Int,
    val henkilökuntaaRyhmässä: Int?,
    val henkilökuntaaLäsnä: Int?,
    val kasvatusvastuullistenLkmYksikössä: Int,
    val ryhmänLapsimäärä: Int,
)

data class DWDailyUnitsOccupanciesConfirmed(
    val pvm: LocalDate?,
    val toimintayksikköId: UUID,
    val toimintayksikkö: String,
    val kasvattajienLkm: Int?,
    val sijoituksienLkm: Int,
    val täyttöasteSumma: Int,
    val täyttöasteProsentteina: Double?,
)

data class DWDailyUnitsOccupanciesRealized(
    val pvm: LocalDate?,
    val toimintayksikköId: UUID,
    val toimintayksikkö: String,
    val kasvattajienLkm: Int?,
    val sijoituksienLkm: Int,
    val käyttöasteSumma: Int,
    val käyttöasteProsentteina: Double?,
)

data class DWDaycareAssistance(
    val pvm: LocalDate,
    val lapsenId: UUID,
    val tuentarveVarhaiskasvatuksessa: DaycareAssistanceLevel,
    val aloitusPvm: String,
    val loppuPvm: LocalDate,
)

data class DWFeeDecision(
    val aikaleima: LocalDateTime,
    val maksupäätöksenNumero: Long?,
    val maksupäätösId: UUID,
    val alkupvm: String,
    val loppupvm: LocalDate,
    val huojennustyyppi: FeeDecisionType,
    val perhekoko: Int,
    val kokonaismaksu: Int,
    val tila: FeeDecisionStatus,
    val lapsiId: UUID,
    val lapsikohtainenMaksu: Int,
    val toimintamuoto: PlacementType,
    val palvelualue: String,
    val palvelualueId: UUID,
    val toimipaikka: String,
    val toimipaikkaId: UUID,
    val kustannuspaikka: String?,
)

data class DWOtherAssistanceMeasure(
    val pvm: LocalDate,
    val lapsenId: UUID,
    val muuToimi: OtherAssistanceMeasureType,
    val aloitusPvm: String,
    val loppuPvm: LocalDate,
)

data class DWPlacement(
    val aikaleima: LocalDate,
    val lapsenId: UUID,
    val toimintayksikkö: String,
    val toimintayksikköId: UUID,
    val sijoituksenAlkupvm: LocalDate,
    val sijoituksenLoppupvm: LocalDate,
    val sijoitustyyppi: PlacementType,
    val ryhmä: String,
    val ryhmäId: UUID,
    val ryhmänAlkupvm: LocalDate,
    val ryhmänLoppupvm: LocalDate?,
    val palveluntarve: String?,
    val palveluntarveId: UUID?,
    val palveluntarpeenAlkupvm: LocalDate?,
    val palveluntarpeenLoppupvm: LocalDate?,
)

data class DWPreschoolAssistance(
    val pvm: LocalDate,
    val lapsenId: UUID,
    val tuentarveEsiopetuksessa: PreschoolAssistanceLevel,
    val aloitusPvm: String,
    val loppuPvm: LocalDate,
)

data class DWUnitAndGroup(
    val aikaleima: LocalDateTime,
    val toimintayksikkö: String,
    val toimintayksikköId: UUID,
    val toimintayksikönAlkupvm: LocalDate?,
    val toimintayksikönLoppupvm: LocalDate?,
    val toimintamuoto: List<CareType>,
    val järjestämistapa: ProviderType,
    val katuosoite: String,
    val postinumero: String,
    val postitoimipaikka: String,
    val toimintayksikönLaskennallinenLapsimäärä: Int,
    val palvelualue: String,
    val palvelualueId: UUID,
    val dwKustannuspaikka: String?,
    val ryhmä: String,
    val ryhmäId: UUID,
    val ryhmänAlkupvm: LocalDate,
    val ryhmänLoppupvm: LocalDate?,
)

data class DWVoucherValueDecision(
    val aikaleima: LocalDateTime,
    val arvopäätöksenNumero: Long?,
    val alkupvm: LocalDate,
    val loppupvm: LocalDate,
    val huojennustyyppi: VoucherValueDecisionType,
    val perhekoko: Int,
    val palvelusetelinArvo: Int,
    val omavastuuosuus: Int,
    val lapsenId: UUID,
    val toimintamuoto: PlacementType?,
    val tila: VoucherValueDecisionStatus,
    val palvelualue: String,
    val palvelualueId: UUID,
    val toimipaikka: String,
    val toimipaikkaId: UUID,
)
