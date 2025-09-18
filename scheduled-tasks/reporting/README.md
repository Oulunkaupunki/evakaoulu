<!--
SPDX-FileCopyrightText: 2024 City of Oulu

SPDX-License-Identifier: LGPL-2.1-or-later
-->

# Data Warehouse Integration (DW)
eVaka data is sent to dedicated data warehouse daily through a scheduled task. DW reports (csv -format) are exported to
the citys SFTP and from there DW fetches the exported csv -reports. The scheduled task runs
[db_to_csv.sh bash script](./db_to_csv.sh), which does all the data querying and exporting to SFTP. All exported reports are also
backed up to eVaka environments S3 storage just in case.

All reports are made via SQL scripts/queries found under [/sql folder](./sql). Every report is customized and made based
on a concrete need. Below is briefly described the contents of each DW report and database enum types used in them:

## Reports

### Absences
Absences -report contains all absences from the last three months.

SQL-script: [DW-Absences.sql](./sql/DW-Absences.sql)

Fields in csv -report:

| Name                | Type                                         |
|---------------------|----------------------------------------------|
| lapsenID            | UUID                                         |
| poissaolonpvm       | timestamp                                    |
| poissaolontyyppi    | Enum: fi.espoo.evaka.absence.AbsenceType     |
| poissaolonkategoria | Enum: fi.espoo.evaka.absence.AbsenceCategory |
| sijoitustyyppi      | Enum: fi.espoo.evaka.placement.PlacementType |


### Application info
Application info -report contains all applications from the last 12 months.

SQL script: [DW-Application_info.sql](./sql/DW-Application_info.sql)

Fields in csv -report:

| Name               | Type                                               |
|--------------------|----------------------------------------------------|
| tiedoston_ajopaiva | timestamp                                          |
| hakemuksen_id      | UUID                                               |
| hakemus_luotu      | timestamp                                          |
| hakemus_paivitetty | timestamp                                          |
| tyyppi             | Enum: fi.espoo.evaka.application.ApplicationType   |
| tilanne            | Enum: fi.espoo.evaka.application.ApplicationStatus |
| alkupera           | Enum: fi.espoo.evaka.application.ApplicationOrigin |
| siirtohakemus      | Boolean                                            |
| lapsen_id          | UUID                                               |
| yksikot            | String[]                                           |
| haluttu_aloituspvm | timestamp                                          |


### Assistance actions
Assistance actions -report contains all assistance actions from the last three months.

SQL script: [DW-Assistance_actions.sql](./sql/DW-Assistance_actions.sql)

Fields in csv -report:

| Name          | Type                                                                 |
|---------------|----------------------------------------------------------------------|
| pvm           | timestamp                                                            |
| lapsen_id     | UUID                                                                 |
| tukitoimi     | String                                                               |
| muu_tukitoimi | String                                                               |
| aloitus_pvm   | Date                                                                 |
| loppu_pvm     | Date                                                                 |
| tuen_tyyppi   | Enum: fi.espoo.evaka.assistanceaction.AssistanceActionOptionCategory |

### Assistance need decisions
Assistance need decisions -report contains all decisions from the last three months.

SQL script: [DW-Assistance_need_decisions.sql](./sql/DW-Assistance_need_decisions.sql)

Fields in csv -report:

| Name                             | Type                                                                      |
|----------------------------------|---------------------------------------------------------------------------|
| aikaleima                        | timestamp                                                                 |
| päätos_tuesta                    | Number                                                                    |
| lapsen_id                        | UUID                                                                      |
| tuen_alkupvm                     | Date                                                                      |
| tuen_loppupvm                    | Date                                                                      |
| pienennetty_ryhmä                | Boolean                                                                   |
| erityisryhmä                     | Boolean                                                                   |
| pienryhmä                        | Boolean                                                                   |
| ryhmäkohtainen_avustaja          | Boolean                                                                   |
| lapsikohtainen_avustaja          | Boolean                                                                   |
| henkilöresurssien_lisäys         | Boolean                                                                   |
| veon_antama_konsultaatio         | Boolean                                                                   |
| veon_osa_aikainen_opetus         | Boolean                                                                   |
| veon_kokoaikainen_opetus         | Boolean                                                                   |
| tulkitsemis_ja_avustamispalvelut | Boolean                                                                   |
| apuvälineet                      | Boolean                                                                   |
| tuen_taso                        | Enum: fi.espoo.evaka.assistanceneed.decision.AssistanceLevel              |
| tila                             | Enum: fi.espoo.evaka.assistanceneed.decision.AssistanceNeedDecisionStatus |


### Daily info
Daily info contains -report contains all currently active placement related data per child.

SQL script: [DW-Daily_info.sql](./sql/DW-Daily_info.sql)

Fields in csv -report:

| Name                   | Type                                             |
|------------------------|--------------------------------------------------|
| pvm                    | timestamp                                        |
| lapsen_id              | UUID                                             |
| henkilöturvatunnus     | String                                           |
| syntymäaika            | Date                                             |
| kieli                  | String                                           |
| postiosoite            | String                                           |
| postinumero            | String                                           |
| postitoimipaikka       | String                                           |
| kansalaisuudet         | String[]                                         |
| sijoitustyyppi         | Enum: fi.espoo.evaka.placement.PlacementType     |
| sijoitusyksikkö_id     | UUID                                             |
| yksikön_nimi           | String                                           |
| palvelualue_id         | UUID                                             |
| palvelualue            | String                                           |
| toimintamuoto          | Enum: fi.espoo.evaka.daycare.CareType            |
| järjestämistapa        | Enum: fi.espoo.evaka.daycare.domain.ProviderType |
| kustannuspaikka        | String                                           |
| sijoitusryhmä_id       | UUID                                             |
| sijoitusryhmä          | String                                           |
| varahoitoyksikkö_id    | UUID                                             |
| varahoitoyksikkö       | String                                           |
| varahoitoryhmä_id      | UUID                                             |
| varahoitoryhmä         | String                                           |
| palveluntarve_merkitty | Boolean                                          |
| palveluntarve          | String                                           |
| palveluntarve_id       | UUID                                             |
| osapäiväinen           | Boolean                                          |
| osaviikkoinen          | Boolean                                          |
| vuorohoito             | Enum: fi.espoo.evaka.serviceneed.ShiftCareType   |
| tunteja_viikossa       | Number                                           |
| tuentarpeen_kerroin    | Number                                           |
| lapsen_kapasiteetti    | Number                                           |
| poissaolon_syy         | Enum: fi.espoo.evaka.absence.AbsenceType         |


### Daily units and groups attendance
Daily units and groups attendance -report contains daily aggregated staff and child attendance counts per daycare group
from the last week.

SQL script: [DW-Daily_units_and_groups_attendance.sql](./sql/DW-Daily_units_and_groups_attendance.sql)

Fields in csv -report:

| Name                                      | Type      |
|-------------------------------------------|-----------|
| aikaleima                                 | timestamp |
| pvm                                       | Date      |
| toimintayksikkö                           | String    |
| toimintayksikkö_id                        | UUID      |
| toimintapäivät                            | Number[]  |
| vuorohoitoyksikkö                         | Boolean   |
| vuorohoitopäivät                          | Number[]  |
| vuorohoitopyhäpäivänä                     | Boolean   |
| ryhmä                                     | String    |
| ryhmä_id                                  | UUID      |
| toimintayksikön_laskennallinen_lapsimäärä | Number    |
| toimintayksikön_lapsimäärä                | Number    |
| henkilökuntaa_ryhmässä                    | Number    |
| henkilökuntaa_läsnä                       | Number    |
| kasvatusvastuullisten_lkm_yksikössä       | Number    |
| ryhmän_lapsimäärä                         | Number    |


### Daily units occupancy confirmed
Daily units occupancy confirmed -report contains daily aggregated child occupancies confirmed by guardians.

SQL script: [DW-Daily_units_occupancy_confirmed.sql](./sql/DW-Daily_units_occupancy_confirmed.sql)

Fields in csv -report:

| Name                    | Type      |
|-------------------------|-----------|
| pvm                     | timestamp |
| toimintayksikkö_id      | UUID      |
| toimintayksikkö         | String    |
| kasvattajien_lkm        | Number    |
| sijoituksien_lkm        | Number    |
| täyttöaste_summa        | Number    |
| täyttöaste_prosentteina | Number    |


### Daily units occupancy realized
Daily units occupancy confirmed -report contains daily aggregated child realized occupancies.

SQL script: [DW-Daily_units_occupancy_realized.sql](./sql/DW-Daily_units_occupancy_realized.sql)

Fields in csv -report:

| Name                    | Type      |
|-------------------------|-----------|
| pvm                     | timestamp |
| toimintayksikkö_id      | UUID      |
| toimintayksikkö         | String    |
| kasvattajien_lkm        | Number    |
| sijoituksien_lkm        | Number    |
| täyttöaste_summa        | Number    |
| täyttöaste_prosentteina | Number    |


### Daycare assistances
Daycare assistances -report contains all daycare assistances from the last three months.

SQL script: [DW-Daycare_assistances.sql](./sql/DW-Daycare_assistances.sql)

Fields in csv -report:

| Name                           | Type                                                   |
|--------------------------------|--------------------------------------------------------|
| pvm                            | timestamp                                              |
| lapsen_id                      | UUID                                                   |
| tuentarve_varhaiskasvatuksessa | Enum: fi.espoo.evaka.assistance.DaycareAssistanceLevel |
| aloitus_pvm                    | Date                                                   |
| loppu_pvm                      | Date                                                   |


### Fee decicions
Fee decisions -report contains all sent (status = SENT) fee decisions from the last three months.

SQL script: [DW-Fee_decisions.sql](./sql/DW-Fee_decisions.sql)

Fields in csv -report:

| Name                  | Type                                                    |
|-----------------------|---------------------------------------------------------|
| aikaleima             | timestamp                                               |
| maksupäätöksen_numero | Number                                                  |
| maksupäätös_id        | UUID                                                    |
| alkupvm               | Date                                                    |
| loppupvm              | Date                                                    |
| huojennustyyppi       | Enum: fi.espoo.evaka.decision.DecisionType              |
| perhekoko             | Number                                                  |
| kokonaismaksu         | Number                                                  |
| tila                  | Enum: fi.espoo.evaka.invoicing.domain.FeeDecisionStatus |
| lapsi_id              | UUID                                                    |
| lapsikohtainen_maksu  | Number                                                  |
| toimintamuoto         | Enum: fi.espoo.evaka.placement.PlacementType            |
| palvelualue           | String                                                  |
| palvelualue_id        | UUID                                                    |
| toimipaikka           | String                                                  |
| toimipaikka_id        | UUID                                                    |
| kustannuspaikka       | Number                                                  |


### Other assistance measures
Other assistance measures -report contains all other assistance measures from the last three months.

SQL script: [DW-Other_assistance_measures.sql](./sql/DW-Other_assistance_measures.sql)

Fields in csv -report:

| Name        | Type                                                       |
|-------------|------------------------------------------------------------|
| pvm         | timestamp                                                  |
| lapsen_id   | UUID                                                       |
| muu_toimi   | Enum: fi.espoo.evaka.assistance.OtherAssistanceMeasureType |
| aloitus_pvm | Date                                                       |
| loppu_pvm   | Date                                                       |


### Placements
Placements -report contains all active and within two weeks closed placements from the last three months.

SQL script: [DW-Placements.sql](./sql/DW-Placements.sql)

| Name                     | Type                                         |
|--------------------------|----------------------------------------------|
| aikaleima                | timestamp                                    |
| lapsen_id                | UUID                                         |
| toimintayksikkö          | String                                       |
| toimintayksikkö_id       | UUID                                         |
| sijoituksen_alkupvm      | timestamp                                    |
| sijoituksen_loppupvm     | timestamp                                    |
| sijoitustyyppi           | Enum: fi.espoo.evaka.placement.PlacementType |
| ryhmä                    | String                                       |
| ryhmä_id                 | UUID                                         |
| ryhmä_aloityspvm         | timestamp                                    |
| ryhmä_lopetuspvm         | timestamp                                    |
| palveluntarve            | String                                       |
| palveluntarve_id         | UUID                                         |
| palveluntarpeen_alkupvm  | timestamp                                    |
| palveluntarpeen_loppupvm | timestamp                                    |


### Preschool assistances
Preschool assistances -report contains all preschool assistances from the last three months.

SQL script: [DW-Preschool_assistances.sql](./sql/DW-Preschool_assistances.sql)

Fields in csv -report:

| Name                     | Type                                                     |
|--------------------------|----------------------------------------------------------|
| pvm                      | timestamp                                                |
| lapsen_id                | UUID                                                     |
| tuentarve_esiopetuksessa | Enum: fi.espoo.evaka.assistance.PreschoolAssistanceLevel |
| aloitus_pvm              | Date                                                     |
| loppu_pvm                | Date                                                     |


### Units and groups
Units and groups -report gathers general daycare unit and group information from the units that has been open the last
three months.

SQL script: [DW-Units_and_groups.sql](./sql/DW-Units_and_groups.sql)

Fields in csv -report:

| Name                                      | Type                                             |
|-------------------------------------------|--------------------------------------------------|
| aikaleima                                 | timestamp                                        |
| toimintayksikkö                           | String                                           |
| toimintayksikkö_id                        | UUID                                             |
| toimintayksikön_alkupvm                   | Date                                             |
| toimintayksikön_loppupvm                  | Date                                             |
| toimintamuoto                             | Enum: fi.espoo.evaka.daycare.CareType            |
| järjestämistapa                           | Enum: fi.espoo.evaka.daycare.domain.ProviderType |
| katuosoite                                | String                                           |
| postinumero                               | String                                           |
| postitoimipaikka                          | String                                           |
| toimintayksikön_laskennallinen_lapsimäärä | Number                                           |
| palvelualue                               | String                                           |
| palvelualue_id                            | UUID                                             |
| dw_kustannuspaikka                        | String                                           |
| ryhmä                                     | String                                           |
| ryhmä_id                                  | UUID                                             |
| ryhmän_alkupvm                            | Date                                             |
| ryhmän_loppupvm                           | Date                                             |


### Voucher value decisions
Voucher value decisions -report contains all sent (status = SENT) voucher value decisions from the last three months.

SQL script: [DW-Voucher_value_decisions](./sql/DW-Voucher_value_decisions.sql)

Fields in csv -report:

| Name                 | Type                                                             |
|----------------------|------------------------------------------------------------------|
| aikaleima            | timestamp                                                        |
| arvopäätöksen_numero | Number                                                           |
| alkupvm              | Date                                                             |
| loppupvm             | Date                                                             |
| huojennustyyppi      | Enum: fi.espoo.evaka.invoicing.domain.VoucherValueDecisionType   |
| perhekoko            | Number                                                           |
| palvelusetelin_arvo  | Number                                                           |
| omavastuuosuus       | Number                                                           |
| lapsen_id            | UUID                                                             |
| toimintamuoto        | Enum: fi.espoo.evaka.placement.PlacementType                     |
| tila                 | Enum: fi.espoo.evaka.invoicing.domain.VoucherValueDecisionStatus |
| palvelualue          | String                                                           |
| palvelualue_id       | UUID                                                             |
| toimipaikka          | String                                                           |
| toimipaikka_id       | UUID                                                             |

## Enum values

### Types

### AbsenceType
Types of absence.

| Value                | Description                                                                                    |
|----------------------|------------------------------------------------------------------------------------------------|
| OTHER_ABSENCE        | A normal absence that has been informed to the staff                                           |
| SICKLEAVE            | An absence caused by sickness                                                                  |
| PLANNED_ABSENCE      | A planned shift work absence or contract based absence                                         |
| UNKNOWN_ABSENCE      | An absence that has not been informed to the staff                                             |
| FORCE_MAJOURE        | A forced absence (e.g. the daycare is closed)                                                  |
| PARENTLEAVE          | An absence because of parent leave                                                             |
| FREE_ABSENCE         | An absence during a holiday season that fulfils specific requirements for being free of charge |
| UNAUTHORIZED_ABSENCE | An absence during a holiday season that warrants to charge a fine                              |


### AbsenceCategory
Billing category of absence.

| Value       | Description             |
|-------------|-------------------------|
| BILLABLE    | Absence is billable     |
| NONBILLABLE | Absence is not billable |


### ApplicationType
Type of daycare the application is applying to.

| Value     | Description               |
|-----------|---------------------------|
| CLUB      | Club                      |
| DAYCARE   | Early childhood education |
| PRESCHOOL | Pre-primary education     |


### ApplicationOrigin
Origin of application.

| Value      | Description                                                                                                                                |
|------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| ELECTRONIC | Citizen has created application via eVaka                                                                                                  |
| PAPER      | Employee has created application based on paper application from citizen or together with citizen, or application is made programmatically |


### AssistanceActionOptionCategory

| Value     | Description              |
|-----------|--------------------------|
| DAYCARE   | Daycare action option    |
| PRESCHOOL | Pre-school action option |


### CareType
Types of unit providing daycare.

| Value                 | Description           |
|-----------------------|-----------------------|
| CLUB                  | Club                  |
| FAMILY                | Family daycare        |
| CENTRE                | Daycare               |
| GROUP_FAMILY          | Group family daycare  |
| PRESCHOOL             | Pre-primary education |
| PREPARATORY_EDUCATION | Preparatory education |

### DecisionType
Types of daycare used in applications and decisions.

| Value                 | Description                                                |
|-----------------------|------------------------------------------------------------|
| CLUB                  | Club                                                       |
| DAYCARE               | Early childhood education                                  |
| DAYCARE_PART_TIME     | Part-day early childhood education                         |
| PRESCHOOL             | Pre-primary education                                      |
| PRESCHOOL_DAYCARE     | Early childhood education related to pre-primary education |
| PRESCHOOL_CLUB        | Pre-primary education club                                 |
| PREPARATORY_EDUCATION | Preparatory education                                      |

### OtherAssistanceMeasureType
Type of other assistance measure

| Value                       | Description                                           |
|-----------------------------|-------------------------------------------------------|
| TRANSPORT_BENEFIT           | Transport benefit                                     |
| ACCULTURATION_SUPPORT       | Support for child acculturation                       |
| ANOMALOUS_EDUCATION_START   | Non-standard start time of education                  |
| CHILD_DISCUSSION_OFFERED    | Let's Talk about Children discussion has been offered |
| CHILD_DISCUSSION_HELD       | Let's Talk about Children discussion has been held    |
| CHILD_DISCUSSION_COUNSELING | Let's Talk about Children counseling has been held    |

### PlacementType
Type of placement affecting integrations, invoicing, absences, messaging and reservations.

| Value                            | Description                                                                                                 |
|----------------------------------|-------------------------------------------------------------------------------------------------------------|
| CLUB                             | Club                                                                                                        |
| DAYCARE                          | Early childhood education                                                                                   |
| DAYCARE_PART_TIME                | Part time early childhood education                                                                         |
| DAYCARE_FIVE_YEAR_OLDS           | 5-year old early childhood education                                                                        |
| DAYCARE_PART_TIME_FIVE_YEAR_OLDS | 5-year old part time early childhood education                                                              |
| PRESCHOOL                        | Preschool education                                                                                         |
| PRESCHOOL_DAYCARE                | Preschool education and early childhood education                                                           |
| PRESCHOOL_DAYCARE_ONLY           | Early childhood education associated with preschool, but preschool is in other unit                         |
| PRESCHOOL_CLUB                   | Preschool club                                                                                              |
| PREPARATORY                      | Preparatory preschool education                                                                             |
| PREPARATORY_DAYCARE              | Preparatory preschool education and early childhood education                                               |
| PREPARATORY_DAYCARE_ONLY         | Early childhood education associated with preparatory preschool, but preparatory preschool is in other unit |
| TEMPORARY_DAYCARE                | Temporary early childhood education                                                                         |
| TEMPORARY_DAYCARE_PART_DAY       | Temporary part-time early childhood education                                                               |
| SCHOOL_SHIFT_CARE                | School shift care                                                                                           |

### ProviderType
Provider types of daycare.

| Value                   | Description                            |
|-------------------------|----------------------------------------|
| MUNICIPAL               | Municipal                              |
| PURCHASED               | Purchased service                      |
| PRIVATE                 | Private service                        |
| MUNICIPAL_SCHOOL        | Municipal                              |
| PRIVATE_SERVICE_VOUCHER | Private service                        |
| EXTERNAL_PURCHASED      | Purchased service from external source |

### ShiftCareType
Shift care type of service need.

| Value        | Description     |
|--------------|-----------------|
| NONE         | No shift care   |
| INTERMITTENT | Some shift care |
| FULL         | Full shift care |

### VoucherValueDecisionType
Decision type for voucher values.

| Value                  | Description                             |
|------------------------|-----------------------------------------|
| NORMAL                 | Normal decision without relief          |
| RELIEF_REJECTED        | Decision with relief accepted           |
| RELIEF_PARTLY_ACCEPTED | Decision with relief partially accepted |
| RELIEF_ACCEPTED        | Decision with relief rejected           |

### Levels

### AssistanceLevel
Level of support for assistance decision.

| Value                        | Description                                                   |
|------------------------------|---------------------------------------------------------------|
| ASSISTANCE_ENDS              | Special/enhanced assistance ends on date                      |
| ASSISTANCE_SERVICES_FOR_TIME | Assistance services are in effect during validity of decision |
| ENHANCED_ASSISTANCE          | Has enhanced assistance                                       |
| SPECIAL_ASSISTANCE           | Has Special assistance                                        |

### DaycareAssistanceLevel
Levels of daycare support for child.

| Value                         | Description                                   |
|-------------------------------|-----------------------------------------------|
| GENERAL_SUPPORT               | General support, no decision                  |
| GENERAL_SUPPORT_WITH_DECISION | General support with assistance need decision |
| INTENSIFIED_SUPPORT           | Intensified support                           |
| SPECIAL_SUPPORT               | Special support                               |

### PreschoolAssistanceLevel
Levels of support in preschool for child.

| Value                                           | Description                                                                           |
|-------------------------------------------------|---------------------------------------------------------------------------------------|
| INTENSIFIED_SUPPORT                             | Intensified support                                                                   |
| SPECIAL_SUPPORT                                 | Special support without extended compulsory education                                 |
| SPECIAL_SUPPORT_WITH_DECISION_LEVEL_1           | Special support and extended compulsory education                                     |
| SPECIAL_SUPPORT_WITH_DECISION_LEVEL_2           | Special support and extended compulsory education because of developmental disability |
| CHILD_SUPPORT                                   | Personal support for child without extended compulsory education                      |
| CHILD_SUPPORT_AND_EXTENDED_COMPULSORY_EDUCATION | Personal support for child with extended compulsory education                         |
| GROUP_SUPPORT                                   | Group based support                                                                   |

### Statuses

### ApplicationStatus
Status of application is process.

| Value                     | Description                                                                          |
|---------------------------|--------------------------------------------------------------------------------------|
| CREATED                   | Initial status of application                                                        |
| SENT                      | Application is finalized by citizen or employee and is ready to be processed         |
| WAITING_PLACEMENT         | Processing the application has started                                               |
| WAITING_UNIT_CONFIRMATION | Placement proposal has been made, unit supervisor needs to confirm the proposal      |
| WAITING_DECISION          | Proposal is accepted, decision needs to be finalized                                 |
| WAITING_MAILING           | If decision cannot be delivered to citizen electronically physical mailing is needed |
| WAITING_CONFIRMATION      | Decision has been delivered which citizen needs to confirm                           |
| REJECTED                  | Employee has rejected application or citizen has rejected decision                   |
| ACTIVE                    | Application is active                                                                |
| CANCELLED                 | Application is no longer needed and it has been cancelled at some point of process   |

### AssistanceNeedDecisionStatus, FeeDecisionStatus and VoucherValueDecisionStatus
Status of a decision in process.

| Value                      | Description                                                                           |
|----------------------------|---------------------------------------------------------------------------------------|
| DRAFT                      | Initial status of a decision                                                          |
| IGNORED                    | Decision is not finalized but no longer needed                                        |
| WAITING_FOR_SENDING        | Decision is finalized and is waiting to be sent                                       |
| WAITING_FOR_MANUAL_SENDING | Delivery information couldn't be found automatically so manual intervention is needed |
| SENT                       | Decision has been sent to the citizen                                                 |
| ANNULLED                   | Decision is no longer valid                                                           |
