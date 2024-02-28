<!--
SPDX-FileCopyrightText: 2024 City of Oulu

SPDX-License-Identifier: LGPL-2.1-or-later
-->

# Data Warehouse Integration (DW)
eVaka data is sent to dedicated data warehouse daily through a scheduled task. DW reports (csv -format) are exported to the citys SFTP and from there DW fetches the exported csv -reports. The scheduled task runs [db_to_csv.sh bash script](./db_to_csv.sh), which does all the data querying and exporting to SFTP. All exported reports are also backed up to eVaka environments S3 storage just in case.

All reports are made via SQL scripts/queries found under [/sql folder](./sql). Every report is customized and made based on a concrete need. Below is briefly described the contents of each DW report:
   

### Assistance actions
Assistance actions -report contains all assistance actions from the last three months.

SQL script: [DW-Assistance_actions.sql](./sql/DW-Assistance_actions.sql)

Fields in csv -report:

| Name          | Type      |
|---------------|-----------|
| pvm           | timestamp |
| lapsen_id     | UUID      |
| tukitoimi     | String    |
| muu_tukitoimi | String    |
| aloitus_pvm   | Date      |
| loppu_pvm     | Date      |


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
Daily units and groups attendance -report contains daily aggregated staff and child attendance counts per daycare group from the last week.

SQL script: [DW-Daily_units_and_groups_attendance.sql](./sql/DW-Daily_units_and_groups_attendance.sql)

Fields in csv -report:

| Name                                      | Type      |
|-------------------------------------------|-----------|
| aikaleima                                 | timestamp |
| pvm                                       | Date      |
| toimintayksikkö                           | String    |
| toimintayksikkö_id                        | UUID      |
| ryhmä                                     | String    |
| ryhmä_id                                  | UUID      |
| toimintayksikön_laskennallinen_lapsimäärä | Number    |
| toimintayksikön_lapsimäärä                | Number    |
| henkilökuntaa_ryhmässä                    | Number    |
| henkilökuntaa_läsnä                       | Number    |
| kasvatusvastuullisten_lkm_yksikössä       | Number    |
| ryhmän_lapsimäärä                         | Number    |
    

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
Units and groups -report gathers general daycare unit and group information from the units that has been open the last three months.

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