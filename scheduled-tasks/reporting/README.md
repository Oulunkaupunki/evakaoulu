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

| Name          | Type   |
|---------------|--------|
| pvm           |        |
| lapsen_id     |        |
| tukitoimi     |        |
| muu_tukitoimi |        |
| aloitus_pvm   |        |
| loppu_pvm     |        |


### Assistance need decisions
Assistance need decisions -report contains all decisions from the last three months.

SQL script: [DW-Assistance_need_decisions.sql](./sql/DW-Assistance_need_decisions.sql)

Fields in csv -report:

| Name                             | Type   |
|----------------------------------|--------|
| aikaleima                        |        |
| päätos_tuesta                    |        |
| lapsen_id                        |        |
| tuen_alkupvm                     |        |
| tuen_loppupvm                    |        |
| pienennetty_ryhmä                |        |
| erityisryhmä                     |        |
| pienryhmä                        |        |
| ryhmäkohtainen_avustaja          |        |
| lapsikohtainen_avustaja          |        |
| henkilöresurssien_lisäys         |        |
| veon_antama_konsultaatio         |        |
| veon_osa_aikainen_opetus         |        |
| veon_kokoaikainen_opetus         |        |
| tulkitsemis_ja_avustamispalvelut |        |  
| apuvälineet                      |        |  
| tuen_taso                        |        |  
| tila                             |        |


### Daily info
Daily info contains -report contains all currently active placement related data per child.

SQL script: [DW-Daily_info.sql](./sql/DW-Daily_info.sql)

Fields in csv -report:

| Name                   | Type   |
|------------------------|--------|
| pvm                    |        |
| lapsen_id              |        |
| henkilöturvatunnus     |        |
| syntymäaika            |        |
| kieli                  |        |
| postiosoite            |        |
| postinumero            |        |
| postitoimipaikka       |        |
| kansalaisuudet         |        |
| sijoitustyyppi         |        |
| sijoitusyksikkö_id     |        |
| yksikön_nimi           |        |
| palvelualue_id         |        |
| palvelualue            |        |
| toimintamuoto          |        |
| järjestämistapa        |        |
| kustannuspaikka        |        |
| sijoitusryhmä_id       |        |
| sijoitusryhmä          |        |
| varahoitoyksikkö_id    |        |
| varahoitoyksikkö       |        |
| varahoitoryhmä_id      |        |
| varahoitoryhmä         |        |
| palveluntarve_merkitty |        |
| palveluntarve          |        |
| palveluntarve_id       |        |
| osapäiväinen           |        |
| osaviikkoinen          |        |
| vuorohoito             |        |
| tunteja_viikossa       |        |
| tuentarpeen_kerroin    |        |
| lapsen_kapasiteetti    |        |
| poissaolon_syy         |        |


### Daily units and groups attendance
Daily units and groups attendance -report contains daily aggregated staff and child attendance counts per daycare group from the last week.

SQL script: [DW-Daily_units_and_groups_attendance.sql](./sql/DW-Daily_units_and_groups_attendance.sql)

Fields in csv -report:

| Name                                      | Type   |
|-------------------------------------------|--------|
| aikaleima                                 |        |
| pvm                                       |        |
| toimintayksikkö                           |        |
| toimintayksikkö_id                        |        |
| ryhmä                                     |        |
| ryhmä_id                                  |        |
| toimintayksikön_laskennallinen_lapsimäärä |        |
| toimintayksikön_lapsimäärä                |        |
| henkilökuntaa_ryhmässä                    |        |
| henkilökuntaa_läsnä                       |        |
| kasvatusvastuullisten_lkm_yksikössä       |        |
| ryhmän_lapsimäärä                         |        |
    

### Daycare assistances
Daycare assistances -report contains all daycare assistances from the last three months.

SQL script: [DW-Daycare_assistances.sql](./sql/DW-Daycare_assistances.sql)

Fields in csv -report:

| Name                           | Type   |
|--------------------------------|--------|
| pvm                            |        |
| lapsen_id                      |        |
| tuentarve_varhaiskasvatuksessa |        |
| aloitus_pvm                    |        |
| loppu_pvm                      |        |


### Fee decicions
Fee decisions -report contains all sent (status = SENT) fee decisions from the last three months.

SQL script: [DW-Fee_decisions.sql](./sql/DW-Fee_decisions.sql)

Fields in csv -report:

| Name                  | Type   |
|-----------------------|--------|
| aikaleima             |        |
| maksupäätöksen_numero |        |
| maksupäätös_id        |        |
| alkupvm               |        |
| loppupvm              |        |
| huojennustyyppi       |        |
| perhekoko             |        |
| kokonaismaksu         |        |
| tila                  |        |
| lapsi_id              |        |
| lapsikohtainen_maksu  |        |
| toimintamuoto         |        |
| palvelualue           |        |
| palvelualue_id        |        |
| toimipaikka           |        |
| toimipaikka_id        |        |
| kustannuspaikka       |        |


### Other assistance measures
Other assistance measures -report contains all other assistance measures from the last three months.

SQL script: [DW-Other_assistance_measures.sql](./sql/DW-Other_assistance_measures.sql)

Fields in csv -report:

| Name        | Type   |
|-------------|--------|
| pvm         |        |
| lapsen_id   |        |
| muu_toimi   |        |
| aloitus_pvm |        |
| loppu_pvm   |        |


### Preschool assistances
Preschool assistances -report contains all preschool assistances from the last three months.

SQL script: [DW-Preschool_assistances.sql](./sql/DW-Preshcool_assistances.sql)

Fields in csv -report:

| Name                     | Type   |
|--------------------------|--------|
| pvm                      |        |
| lapsen_id                |        |
| tuentarve_esiopetuksessa |        |
| aloitus_pvm              |        |
| loppu_pvm                |        |


### Units and groups
Units and groups -report gathers general daycare unit and group information from the units that has been open the last three months.

SQL script: [DW-Units_and_groups.sql](./sql/DW-Units_and_groups.sql)

Fields in csv -report:

| Name                                      | Type   |
|-------------------------------------------|--------|
| aikaleima                                 |        |
| toimintayksikkö                           |        |
| toimintayksikkö_id                        |        |
| toimintayksikön_alkupvm                   |        |
| toimintayksikön_loppupvm                  |        |
| toimintamuoto                             |        |
| järjestämistapa                           |        |
| katuosoite                                |        |
| postinumero                               |        |
| postitoimipaikka                          |        |
| toimintayksikön_laskennallinen_lapsimäärä |        |
| palvelualue                               |        |
| palvelualue_id                            |        |
| dw_kustannuspaikka                        |        |
| ryhmä                                     |        |
| ryhmä_id                                  |        |
| ryhmän_alkupvm                            |        |
| ryhmän_loppupvm                           |        |
                 

### Voucher value decisions
Voucher value decisions -report contains all sent (status = SENT) voucher value decisions from the last three months.

SQL script: [DW-Voucher_value_decisions](./sql/DW-Voucher_value_decisions)

Fields in csv -report:

| Name                 | Type   |
|----------------------|--------|
| aikaleima            |        |
| arvopäätöksen_numero |        |
| alkupvm              |        |
| loppupvm             |        |
| huojennustyyppi      |        |
| perhekoko            |        |
| palvelusetelin_arvo  |        |
| omavastuuosuus       |        |
| lapsen_id            |        |
| toimintamuoto        |        |
| tila                 |        |
| palvelualue          |        |
| palvelualue_id       |        |
| toimipaikka          |        |
| toimipaikka_id       |        |    