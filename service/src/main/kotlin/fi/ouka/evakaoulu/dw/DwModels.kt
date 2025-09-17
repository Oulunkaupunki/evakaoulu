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
import java.util.UUID

data class DwAbsence(
    val lapsenid: UUID,
    val poissaolonpvm: LocalDate,
    val poissaolontyyppi: AbsenceType,
    val poissaolonkategoria: AbsenceCategory,
    val sijoitustyyppi: PlacementType,
)

data class DwApplicationInfo(
    val hakemuksenId: UUID,
    val hakemusLuotu: String,
    val hakemustaPaivitetty: String,
    val tyyppi: ApplicationType,
    val tilanne: ApplicationStatus,
    val alkupera: ApplicationOrigin,
    val siirtohakemus: Boolean,
    val lapsenId: UUID,
    val syntymaaika: LocalDate,
    val yksikot: String,
    val haluttuAloituspaiva: String?,
    val yksikkoNimi: String,
    val alueId: UUID,
    val alueNimi: String,
)

data class DwAssistanceAction(
    val pvm: String,
    val lapsenId: UUID,
    val tukitoimi: String?,
    val muuTukitoimi: String,
    val aloitusPvm: LocalDate,
    val loppuPvm: LocalDate,
)

data class DwAssistanceNeedDecision(
    val aikaleima: String,
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

data class DwDailyInfo(
    val pvm: String,
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

data class DwDailyUnitsAndGroupsAttendance(
    val aikaleima: String,
    val pvm: LocalDate?,
    val toimintayksikkö: String,
    val toimintayksikköId: UUID,
    val toimintapäivät: List<Int>,
    val vuorohoitoyksikkö: Boolean?,
    val vuorohoitopäivät: List<Int>?,
    val vuorohoitopyhäpäivinä: Boolean,
    val ryhmä: String,
    val ryhmäId: UUID,
    val toimintayksikönLaskennallinenLapsimäärä: Int?,
    val toimintayksikönLapsimäärä: Int?,
    val henkilökuntaaRyhmässä: Int?,
    val henkilökuntaaLäsnä: Double?,
    val kasvatusvastuullistenLkmYksikössä: Double?,
    val ryhmänLapsimäärä: Int?,
)

data class DwDailyUnitsOccupanciesConfirmed(
    val pvm: LocalDate,
    val toimintayksikköId: UUID,
    val toimintayksikkö: String,
    val kasvattajienLkm: Double?,
    val sijoituksienLkm: Int?,
    val täyttöasteSumma: Double?,
    val täyttöasteProsentteina: Double?,
)

data class DwDailyUnitsOccupanciesRealized(
    val pvm: LocalDate,
    val toimintayksikköId: UUID,
    val toimintayksikkö: String,
    val kasvattajienLkm: Double?,
    val sijoituksienLkm: Int?,
    val käyttöasteSumma: Double?,
    val käyttöasteProsentteina: Double?,
)

data class DwDaycareAssistance(
    val pvm: String,
    val lapsenId: UUID,
    val tuentarveVarhaiskasvatuksessa: DaycareAssistanceLevel,
    val aloitusPvm: String,
    val loppuPvm: LocalDate,
)

data class DwFeeDecision(
    val aikaleima: String,
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

data class DwOtherAssistanceMeasure(
    val pvm: String,
    val lapsenId: UUID,
    val muuToimi: OtherAssistanceMeasureType,
    val aloitusPvm: String,
    val loppuPvm: LocalDate,
)

data class DwPlacement(
    val aikaleima: String,
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

data class DwPreschoolAssistance(
    val pvm: String,
    val lapsenId: UUID,
    val tuentarveEsiopetuksessa: PreschoolAssistanceLevel,
    val aloitusPvm: String,
    val loppuPvm: LocalDate,
)

data class DwUnitAndGroup(
    val aikaleima: String,
    val toimintayksikkö: String,
    val toimintayksikköId: UUID,
    val toimintayksikönAlkupvm: LocalDate?,
    val toimintayksikönLoppupvm: LocalDate?,
    val toimintamuoto: List<CareType>,
    val järjestämistapa: ProviderType,
    val katuosoite: String,
    val postinumero: String,
    val postitoimipaikka: String,
    val toimintayksikönLaskennallinenLapsimäärä: Int?,
    val palvelualue: String,
    val palvelualueId: UUID,
    val dwKustannuspaikka: String?,
    val ryhmä: String,
    val ryhmäId: UUID,
    val ryhmänAlkupvm: LocalDate,
    val ryhmänLoppupvm: LocalDate?,
)

data class DwVoucherValueDecision(
    val aikaleima: String,
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
