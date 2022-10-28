-- SPDX-FileCopyrightText: 2021 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

INSERT INTO service_need_option
    (id, name_fi, name_sv, name_en, contract_days_per_month, valid_placement_type, default_option, fee_coefficient, occupancy_coefficient, daycare_hours_per_week, part_day, part_week, fee_description_fi, fee_description_sv, voucher_value_description_fi, voucher_value_description_sv, display_order, occupancy_coefficient_under_3y, show_for_citizen)
VALUES
    -- DAYCARE
    ('de463972-9f97-11ec-a22c-931f5a294cea','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Full-time early childhood education',NULL,'DAYCARE',TRUE,1.0,1.0,40,FALSE,FALSE,'Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus', null, 1.75, true),
    ('86ef70a0-bf85-11eb-91e6-1fb57a101161','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Full-time early childhood education',NULL,'DAYCARE',FALSE,1.0,1.0,40,FALSE,FALSE,'Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus', 1, 1.75, true),
    ('503590f0-b961-11eb-b520-53740af3f7ee','Kokoaikainen varhaiskasvatus 10 pv/kk','Kokoaikainen varhaiskasvatus 10 pv/kk','Full-time early childhood education 10 d/M',10,'DAYCARE',FALSE,0.5,0.54,20.2,FALSE,FALSE,'Kokoaikainen varhaiskasvatus 10 pv/kk','Kokoaikainen varhaiskasvatus 10 pv/kk','Kokoaikainen varhaiskasvatus 10 pv/kk','Kokoaikainen varhaiskasvatus 10 pv/kk', 2, 1.75, true),
    ('503591ae-b961-11eb-b521-1fca99358eef','Kokoaikainen varhaiskasvatus 13 pv/kk','Kokoaikainen varhaiskasvatus 13 pv/kk','Full-time early childhood education 13 d/M',13,'DAYCARE',FALSE,0.75,0.77,26.3,FALSE,FALSE,'Kokoaikainen varhaiskasvatus 13 pv/kk','Kokoaikainen varhaiskasvatus 13 pv/kk','Kokoaikainen varhaiskasvatus 13 pv/kk','Kokoaikainen varhaiskasvatus 13 pv/kk', 3, 1.75, true),
    ('c2886f84-9ef2-11ec-8538-734ab2ac6c71','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','Early childhood education under 7-h day',NULL,'DAYCARE',FALSE,0.75,1.0,35,FALSE,FALSE,'Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä', 1003,1.75, true),
    ('c288a058-9ef2-11ec-853e-9bcaa26ea729','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','Private early childhood education',NULL,'DAYCARE',FALSE,1.0,1.0,40,FALSE,FALSE,'Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus', 4, 1.75, true),
    ('c288a0c6-9ef2-11ec-853f-47b02ebd51bb','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','Private early childhood education service voucher',NULL,'DAYCARE',FALSE,1.0,1.0,40,FALSE,FALSE,'Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse', 5,1.75, false),
    ('19094188-130d-11ed-a572-473907d23b65','Yksityinen perhepäivähoito palse','Yksityinen perhepäivähoito palse','Private family daycare service voucher',NULL,'DAYCARE',FALSE,1.0,1.0,40,FALSE,FALSE,'Yksityinen perhepäivähoito palse','Yksityinen perhepäivähoito palse','Yksityinen perhepäivähoito palse','Yksityinen perhepäivähoito palse', 6,1, false),

    -- DAYCARE_PART_TIME
    ('de463bd4-9f97-11ec-a22d-4791bc036bd9','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Early childhood education under 5-h day',NULL,'DAYCARE_PART_TIME',TRUE,0.6,0.54,25,FALSE,FALSE,'Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä', null,1.75, true),
    ('86ef7370-bf85-11eb-91e7-6fcd728c518d','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Early childhood education under 5-h day',NULL,'DAYCARE_PART_TIME',FALSE,0.6,0.54,25,FALSE,FALSE,'Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä', 1000,1.75, true),
    ('c2886fca-9ef2-11ec-8539-1b9652921b6d','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','Private part time 12-13d/m service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.65,1.0,26.3,FALSE,FALSE,'Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse', null,1.75, false),
    ('c2889e1e-9ef2-11ec-853a-aff6768857cb','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','Private part time 29h/week service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.6,1.0,29,FALSE,FALSE,'Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse', null,1.75, false),
    ('c2889ef0-9ef2-11ec-853b-2f545a699ef0','Yksityinen osapäivä','Yksityinen osapäivä','Private part time',NULL,'DAYCARE_PART_TIME',FALSE,0.5,0.54,20,TRUE,FALSE,'Yksityinen osapäivä','Yksityinen osapäivä','Yksityinen osapäivä','Yksityinen osapäivä', null,1.75, true),
    ('c2889f72-9ef2-11ec-853c-3bc6a35d44a3','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','Private part time 4h service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.5,0.54,20,TRUE,FALSE,'Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse', null,1.75, false),
    ('50359334-b961-11eb-b525-f3febdfea5d3','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','Private part time 20h service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.5,0.54,20,FALSE,TRUE,'Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse', null,1.75, false),
    ('86495b70-130d-11ed-a573-bfda992853b8','Yksityinen perhepäivähoito osaviikko 20h palse','Yksityinen perhepäivähoito osaviikko 20h palse','Private family daycare part time 20h service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.55,0.54,20,FALSE,TRUE,'Yksityinen perhepäivähoito osaviikko 20h palse','Yksityinen perhepäivähoito osaviikko 20h palse','Yksityinen perhepäivähoito osaviikko 20h palse','Yksityinen perhepäivähoito osaviikko 20h palse', null,1, false),
    ('d146f678-130d-11ed-a574-c39e98171347','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Private family daycare part time 12-13d/m service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.7,1.0,26.3,FALSE,FALSE,'Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse', null,1, false),
    ('07a1bde8-130e-11ed-a575-bbe09459e8d0','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Private family daycare part time 29h/week service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.7,1.0,29,FALSE,FALSE,'Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse', null,1, false),
    ('5c8bdebc-3350-11ed-a1e8-23631d9473d4','Yksityinen perhepäivähoito osapäivä 4h palse','Yksityinen perhepäivähoito osapäivä 4h palse','Private family daycare part time 4h service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.5,0.54,20,TRUE,FALSE,'Yksityinen perhepäivähoito osapäivä 4h palse','Yksityinen perhepäivähoito osapäivä 4h palse','Yksityinen perhepäivähoito osapäivä 4h palse','Yksityinen perhepäivähoito osapäivä 4h palse', null,1.75, false),

    -- PRESCHOOL
    ('de463c38-9f97-11ec-a22e-97907801ecdc', 'Esiopetus 4h ','Esiopetus 4h ','Pre-school education 4h ',NULL,'PRESCHOOL',TRUE,0,0.54,20,TRUE,FALSE,'Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h', null,1.75, true),
    ('c288a152-9ef2-11ec-8540-2fc7b01eb49e','Esiopetus 4h ','Esiopetus 4h ','Pre-school education 4h ',NULL,'PRESCHOOL',FALSE,0,0.54,20,TRUE,FALSE,'Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h', 2000,1.75, true),
    ('c288a26a-9ef2-11ec-8541-57b8edf0098f','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','A two-year pre-school trial',NULL,'PRESCHOOL',FALSE,0,0.54,20,TRUE,FALSE,'Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu', null,1.75, true),
    ('c288a2d8-9ef2-11ec-8542-231141d61e61','Yksityinen esiopetus 4 h palse','Yksityinen esiopetus 4 h palse','Private pre-school education 4 h service voucher',NULL,'PRESCHOOL',FALSE,0.0,0.54,20,TRUE,FALSE,'Yksityinen esiopetus 4 h palse','Yksityinen esioeptus 4 h palse','Yksityinen esioeptus 4 h palse','Yksityinen esioeptus 4 h palse', null,1.75, false),

    -- PRESCHOOL_DAYCARE
    ('de463c92-9f97-11ec-a22f-67d2f441c52f','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Pre-school education + early childhood education',NULL,'PRESCHOOL_DAYCARE',TRUE,0.6,1.0,25,FALSE,FALSE,'Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus', null,1.75, true),
    ('c288a33c-9ef2-11ec-8543-c7f61d98bbff','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Pre-school education + early childhood education',NULL,'PRESCHOOL_DAYCARE',FALSE,0.6,1.0,25,FALSE,FALSE,'Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus', null,1.75, true),
    ('2e0f93a8-e57b-11ec-a452-7f636f92b30c','Esiopetus + varhaiskasvatus 2v','Esiopetus + varhaiskasvatus 2v','Pre-school education + early childhood education 2y',NULL,'PRESCHOOL_DAYCARE',FALSE,0.6,1.0,25,FALSE,FALSE,'Esiopetus + varhaiskasvatus 2v','Esiopetus + varhaiskasvatus 2v','Esiopetus + varhaiskasvatus 2v','Esiopetus + varhaiskasvatus 2v', null,1.75, false),
    ('c288a3aa-9ef2-11ec-8544-072366604c6f','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','Pre-school education + early childhood education less than 3h',NULL,'PRESCHOOL_DAYCARE',FALSE,0.45,1.0,20,FALSE,FALSE,'Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h', null,1.75, true),
    ('c288a3fa-9ef2-11ec-8545-0feaf5efd1b9','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','Pre-school education + early childhood education 10 d/m',10,'PRESCHOOL_DAYCARE',FALSE,0.3,1.0,9.5,FALSE,FALSE,'Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk', null,1.75, true),
    ('c288a440-9ef2-11ec-8546-8f405f297780','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','Pre-school education + early childhood education 13 d/m',13,'PRESCHOOL_DAYCARE',FALSE,0.45,1.0,12.4,FALSE,FALSE,'Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk', null,1.75, true),
    ('91a50d60-3f28-11ed-8217-b35ad6248e8d','Esiopetus + varhaiskasvatus 10 pv/kk 2v','Esiopetus + varhaiskasvatus 10 pv/kk 2v','Pre-school education + early childhood education 10 d/m 2y',10,'PRESCHOOL_DAYCARE',FALSE,0.3,1.0,9.5,FALSE,FALSE,'Esiopetus + varhaiskasvatus 10 pv/kk 2v','Esiopetus + varhaiskasvatus 10 pv/kk 2v','Esiopetus + varhaiskasvatus 10 pv/kk 2v','Esiopetus + varhaiskasvatus 10 pv/kk 2v', null,1.75, false),
    ('bf4b4946-3f28-11ed-8218-0392ee6d4528','Esiopetus + varhaiskasvatus 13 pv/kk 2v','Esiopetus + varhaiskasvatus 13 pv/kk 2v','Pre-school education + early childhood education 13 d/m 2y',13,'PRESCHOOL_DAYCARE',FALSE,0.45,1.0,12.4,FALSE,FALSE,'Esiopetus + varhaiskasvatus 13 pv/kk 2v','Esiopetus + varhaiskasvatus 13 pv/kk 2v','Esiopetus + varhaiskasvatus 13 pv/kk 2v','Esiopetus + varhaiskasvatus 13 pv/kk 2v', null,1.75, false),
    ('99ab7baa-4083-11ed-8078-87d60cea9aea','Esiopetus + varhaiskasvatus alle 3h 2v','Esiopetus + varhaiskasvatus alle 3h 2v','Pre-school education + early childhood education less than 3h 2y',NULL,'PRESCHOOL_DAYCARE',FALSE,0.45,1.0,20,FALSE,FALSE,'Esiopetus + varhaiskasvatus alle 3h 2v','Esiopetus + varhaiskasvatus alle 3h 2v','Esiopetus + varhaiskasvatus alle 3h 2v','Esiopetus + varhaiskasvatus alle 3h 2v', null,1.75, false),
    ('c288a490-9ef2-11ec-8547-ab2d0b032c1c','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','Private pre-school education + early childhood education service voucher',NULL,'PRESCHOOL_DAYCARE',FALSE,0.6,1.0,25,FALSE,FALSE,'Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse', null,1.75, false),

    -- CLUB
    ('de463cec-9f97-11ec-a230-bb19e6bd6663','Kerho 2 x viikko','Kerho 2 x viikko','Club 2 x week',NULL,'CLUB',TRUE,0,1.0,10,FALSE,FALSE,'Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko', null,1.75, true),
    ('c288a4d6-9ef2-11ec-8548-53f49e3cb87b','Kerho 2 x viikko','Kerho 2 x viikko','Club 2 x week',NULL,'CLUB',FALSE,0,1.0,10,FALSE,FALSE,'Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko', null,1.75, true),
    ('c288a58a-9ef2-11ec-8549-2f93c47040bf','Kerho 3 x viikko','Kerho 3 x viikko','Club 3 x week',NULL,'CLUB',FALSE,0,1.0,10,FALSE,FALSE,'Kerho 3 x viikko','Kerho 3 x viikko','Kerho 3 x viikko','Kerho 3 x viikko', null,1.75, true),
    ('c288a5d0-9ef2-11ec-854a-b33b00871752','Perhekerho','Perhekerho','Family club',NULL,'CLUB',FALSE,0,1.0,10,FALSE,FALSE,'Perhekerho','Perhekerho','Perhekerho','Perhekerho', null,1.75, true),
    ('c288a620-9ef2-11ec-854b-13e81732636f','Tilapäinen kerho','Tilapäinen kerho','Temporary club',NULL,'CLUB',FALSE,0,1.0,10,FALSE,FALSE,'Tilapäinen kerho','Tilapäinen kerho','Tilapäinen kerho','Tilapäinen kerho', null,1.75, true),
    ('c288a666-9ef2-11ec-854c-8bcc8b9a9307','Yksityinen kerho palse','Yksityinen kerho palse','Private club service voucher',NULL,'CLUB',FALSE,1.0,1.0,10,FALSE,FALSE,'Yksityinen kerho palse','Yksityinen kerho palse','Yksityinen kerho palse','Yksityinen kerho palse', null,1.75, false),

    -- TEMPORARY_DAYCARE
    ('de463d32-9f97-11ec-a231-5bcd9b48daeb', 'Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Temporary early childhood education',NULL,'TEMPORARY_DAYCARE',TRUE,1.0,1.0,40,FALSE,FALSE,'Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus', null,1.75, true),
    ('c288a6ac-9ef2-11ec-854d-8ba46873165a','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Temporary early childhood education',NULL,'TEMPORARY_DAYCARE',FALSE,1.0,1.0,40,FALSE,FALSE,'Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus', null,1.75, true),

    -- TEMPORARY_DAYCARE_PART_DAY
    ('7dedd9a8-a04c-11ec-a72b-3779d97aac97','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','temporary part-time early childhood education',NULL,'TEMPORARY_DAYCARE_PART_DAY',TRUE,1.0,0.54,20,TRUE,FALSE,'Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus', null,1.75, true),
    ('c288a706-9ef2-11ec-854e-8fec0360d917','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','temporary part-time early childhood education',NULL,'TEMPORARY_DAYCARE_PART_DAY',FALSE,1.0,0.54,20,TRUE,FALSE,'Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus', null,1.75, true),

    -- PREPARATORY
    ('de463d82-9f97-11ec-a232-a782189e7c84', 'Valmistava esiopetus','Valmistava esiopetus','Preparatory pre-school education',NULL,'PREPARATORY',TRUE,0,0.54,25,TRUE,FALSE,'Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus', null,1.75, true),
    ('c288a74c-9ef2-11ec-854f-935955a88b27','Valmistava esiopetus','Valmistava esiopetus','Preparatory pre-school education',NULL,'PREPARATORY',FALSE,0,0.54,25,TRUE,FALSE,'Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus', null,1.75, true),

    -- PREPARATORY_DAYCARE
    ('05999cd6-1fa3-11ed-b545-8f2c5e506764','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Preparatory pre-school education + early childhood education',NULL,'PREPARATORY_DAYCARE',TRUE,0.6,1.0,25,TRUE,FALSE,'Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus', null,1.75, true),
    ('53bad240-1fa3-11ed-b546-eb62f017d64a','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Preparatory pre-school education + early childhood education',NULL,'PREPARATORY_DAYCARE',FALSE,0.6,1.0,25,TRUE,FALSE,'Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus', null,1.75, true),
    ('adff4b3e-4607-11ed-b4df-b338fa9c8022','Valmistava esiopetus + varhaiskasvatus 10 pv/kk','Valmistava esiopetus + varhaiskasvatus 10 pv/kk','Preparatory pre-school education + early childhood education 10 d/m',10,'PREPARATORY_DAYCARE',FALSE,0.3,1.0,9.5,FALSE,FALSE,'Valmistava esiopetus + varhaiskasvatus 10 pv/kk','Valmistava esiopetus + varhaiskasvatus 10 pv/kk','Valmistava esiopetus + varhaiskasvatus 10 pv/kk','Valmistava esiopetus + varhaiskasvatus 10 pv/kk', null,1.75, true),
    ('274acbd0-4608-11ed-b4e0-07ecacd56b03','Valmistava esiopetus + varhaiskasvatus 13 pv/kk','Valmistava esiopetus + varhaiskasvatus 13 pv/kk','Preparatory pre-school education + early childhood education 13 d/m',13,'PREPARATORY_DAYCARE',FALSE,0.45,1.0,12.4,FALSE,FALSE,'Valmistava esiopetus + varhaiskasvatus 13 pv/kk','Valmistava esiopetus + varhaiskasvatus 13 pv/kk','Valmistava esiopetus + varhaiskasvatus 13 pv/kk','Valmistava esiopetus + varhaiskasvatus 13 pv/kk', null,1.75, true),
    ('4e62e91e-4608-11ed-b4e1-ab7b3db89639','Valmistava esiopetus + varhaiskasvatus alle 3h','Valmistava esiopetus + varhaiskasvatus alle 3h','Preparatory pre-school education + early childhood education less than 3h',NULL,'PREPARATORY_DAYCARE',FALSE,0.45,1.0,20,FALSE,FALSE,'Valmistava esiopetus + varhaiskasvatus alle 3h','Valmistava esiopetus + varhaiskasvatus alle 3h','Valmistava esiopetus + varhaiskasvatus alle 3h','Valmistava esiopetus + varhaiskasvatus alle 3h', null,1.75, true)


ON CONFLICT (id) DO
UPDATE SET
    name_fi = EXCLUDED.name_fi,
    name_sv = EXCLUDED.name_sv,
    name_en = EXCLUDED.name_en,
    contract_days_per_month = EXCLUDED.contract_days_per_month,
    valid_placement_type = EXCLUDED.valid_placement_type,
    default_option = EXCLUDED.default_option,
    fee_coefficient = EXCLUDED.fee_coefficient,
    occupancy_coefficient = EXCLUDED.occupancy_coefficient,
    daycare_hours_per_week = EXCLUDED.daycare_hours_per_week,
    part_day = EXCLUDED.part_day,
    part_week = EXCLUDED.part_week,
    fee_description_fi = EXCLUDED.fee_description_fi,
    fee_description_sv = EXCLUDED.fee_description_sv,
    voucher_value_description_fi = EXCLUDED.voucher_value_description_fi,
    voucher_value_description_sv = EXCLUDED.voucher_value_description_sv,
    display_order = EXCLUDED.display_order,
    occupancy_coefficient_under_3y = EXCLUDED.occupancy_coefficient_under_3y,
    show_for_citizen = EXCLUDED.show_for_citizen
WHERE
    service_need_option.name_fi <> EXCLUDED.name_fi OR
    service_need_option.name_sv <> EXCLUDED.name_sv OR
    service_need_option.name_en <> EXCLUDED.name_en OR
    service_need_option.contract_days_per_month <> EXCLUDED.contract_days_per_month OR
    service_need_option.valid_placement_type <> EXCLUDED.valid_placement_type OR
    service_need_option.default_option <> EXCLUDED.default_option OR
    service_need_option.fee_coefficient <> EXCLUDED.fee_coefficient OR
    service_need_option.occupancy_coefficient <> EXCLUDED.occupancy_coefficient OR
    service_need_option.daycare_hours_per_week <> EXCLUDED.daycare_hours_per_week OR
    service_need_option.part_day <> EXCLUDED.part_day OR
    service_need_option.part_week <> EXCLUDED.part_week OR
    service_need_option.fee_description_fi <> EXCLUDED.fee_description_fi OR
    service_need_option.fee_description_sv <> EXCLUDED.fee_description_sv OR
    service_need_option.voucher_value_description_fi <> EXCLUDED.voucher_value_description_fi OR
    service_need_option.voucher_value_description_sv <> EXCLUDED.voucher_value_description_sv OR
    service_need_option.display_order <> EXCLUDED.display_order OR
    service_need_option.occupancy_coefficient_under_3y <> EXCLUDED.occupancy_coefficient_under_3y OR
    service_need_option.show_for_citizen <> EXCLUDED.show_for_citizen;

INSERT INTO service_need_option_voucher_value (
    id,service_need_option_id,validity,base_value,coefficient,value,base_value_under_3y,coefficient_under_3y,value_under_3y
)
VALUES

    --c288a2d8-9ef2-11ec-8542-231141d61e61  Yksityinen esiopetus 4 h palse
    ('a069ba6a-1185-11ed-80ed-9fe0cbbc284c','c288a2d8-9ef2-11ec-8542-231141d61e61',daterange('2022-08-01', NULL, '[]'),86800,0.5,43400,133200,1.0,133200),
    ('1fb0a03e-3d70-11ed-afc6-97a805608d77','c288a2d8-9ef2-11ec-8542-231141d61e61',daterange('2020-08-01', '2022-07-31', '[]'),82500,0.5,41200,126600,1.0,126600),
    ('37f1aa02-3d80-11ed-af36-5725e2bfc8f8','c288a2d8-9ef2-11ec-8542-231141d61e61',daterange('2018-08-01', '2019-07-31', '[]'),72500,0.5,36300,122300,1.0,122300),

    ('352f66d5-e50d-4bc3-954d-305736d85272','c288a2d8-9ef2-11ec-8542-231141d61e61',daterange('2019-08-01', '2020-07-31', '[]'),79700,0.5,39900,126600,1.0,126600),

    --c288a490-9ef2-11ec-8547-ab2d0b032c1c	Yksityinen esiopetus + varhaiskasvatus palse
    ('a069ce10-1185-11ed-80ed-4366a9322b9a','c288a490-9ef2-11ec-8547-ab2d0b032c1c',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,0.5,63300),
    ('ba910620-3d70-11ed-afcd-d7219776a7a0','c288a490-9ef2-11ec-8547-ab2d0b032c1c',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,0.5,63300),
    ('3fb77d9c-3d81-11ed-af3d-bf3864a97042','c288a490-9ef2-11ec-8547-ab2d0b032c1c',daterange('2018-08-01', '2019-07-31', '[]'),72500,1.0,72500,122300,0.5,63300),
    ('583c7eb1-856e-41e0-95ca-81138c08ff8a','c288a490-9ef2-11ec-8547-ab2d0b032c1c',daterange('2019-08-01', '2020-07-31', '[]'),79700,1.0,79700,126600,0.5,63300),

    --c2886fca-9ef2-11ec-8539-1b9652921b6d  Yksityinen osa-aikainen 12-13pv/kk palse
    ('a069a408-1185-11ed-80ed-5bee3b23fe54','c2886fca-9ef2-11ec-8539-1b9652921b6d',daterange('2022-08-01', NULL, '[]'),86800,0.7,60700,133200,0.7,93200),
    ('f4c01018-3d6e-11ed-afba-539ff4baca90','c2886fca-9ef2-11ec-8539-1b9652921b6d',daterange('2020-08-01', '2022-07-31', '[]'),82500,0.7,57700,126600,0.7,88600),
    ('df39c0be-3d7c-11ed-af2a-3f019157e75b','c2886fca-9ef2-11ec-8539-1b9652921b6d',daterange('2018-08-01', '2019-07-31', '[]'),72500,0.7,50800,122300,0.7,85600),
    ('03db7f68-4136-4cea-9b7e-6c059ef384dd','c2886fca-9ef2-11ec-8539-1b9652921b6d',daterange('2019-08-01', '2020-07-31', '[]'),79700,0.7,55800,126600,0.7,88600),

    --c2889e1e-9ef2-11ec-853a-aff6768857cb	Yksityinen osa-aikainen 29h/viikko palse
    ('a069a5f2-1185-11ed-80ed-b7c85eff5571','c2889e1e-9ef2-11ec-853a-aff6768857cb',daterange('2022-08-01', NULL, '[]'),86800,0.7,60700,133200,0.7,93200),
    ('170e459a-3d6f-11ed-afbb-9fd7fa3a28ae','c2889e1e-9ef2-11ec-853a-aff6768857cb',daterange('2020-08-01', '2022-07-31', '[]'),82500,0.7,57700,126600,0.7,88600),
    ('4837572a-3d7d-11ed-af2b-cff85bc5bf68','c2889e1e-9ef2-11ec-853a-aff6768857cb',daterange('2018-08-01', '2019-07-31', '[]'),72500,0.7,50800,122300,0.7,85600),
    ('d331d279-6bc2-44b5-bd2d-1cdfa71d0dbb','c2889e1e-9ef2-11ec-853a-aff6768857cb',daterange('2019-08-01', '2020-07-31', '[]'),79700,0.7,55800,122300,0.7,85600),

    --c2889f72-9ef2-11ec-853c-3bc6a35d44a3	Yksityinen osap√§iv√§ 4h palse
    ('a069aa98-1185-11ed-80ed-0f06e4c634d7','c2889f72-9ef2-11ec-853c-3bc6a35d44a3',daterange('2022-08-01', NULL, '[]'),86800,0.55,47700,133200,0.55,73200),
    ('41adcf50-3d6f-11ed-afbd-67c4a6b3ab99','c2889f72-9ef2-11ec-853c-3bc6a35d44a3',daterange('2020-08-01', '2022-07-31', '[]'),82500,0.55,45400,126600,0.55,69600),
    ('73b6654e-3d7d-11ed-af2d-7b59593a459b','c2889f72-9ef2-11ec-853c-3bc6a35d44a3',daterange('2018-08-01', '2019-07-31', '[]'),72500,0.55,39900,122300,0.55,67300),
    ('958e5369-20e6-468d-b0a6-bc667aa73f0a','c2889f72-9ef2-11ec-853c-3bc6a35d44a3',daterange('2019-08-01', '2020-07-31', '[]'),79700,0.55,43800,122300,0.55,67300),

    --50359334-b961-11eb-b525-f3febdfea5d3	Yksityinen osaviikko 20h palse
    ('a069afc0-1185-11ed-80ed-53c4aa412615','50359334-b961-11eb-b525-f3febdfea5d3',daterange('2022-08-01', NULL, '[]'),86800,0.55,47700,133200,0.55,73200),
    ('5f66a1f2-3d6f-11ed-afbe-47e0d1127afb','50359334-b961-11eb-b525-f3febdfea5d3',daterange('2020-08-01', '2022-07-31', '[]'),82500,0.55,45400,126600,0.55,69600),
    ('880cbaac-3d7d-11ed-af2e-bb57ce5b85b8','50359334-b961-11eb-b525-f3febdfea5d3',daterange('2018-08-01', '2019-07-31', '[]'),72500,0.55,39900,122300,0.55,67300),
    ('fe450d3a-a61c-4693-bcff-e1b1fc19b752','50359334-b961-11eb-b525-f3febdfea5d3',daterange('2019-08-01', '2020-07-31', '[]'),79700,0.55,43800,122300,0.55,67300),

    --d146f678-130d-11ed-a574-c39e98171347	Yksityinen perhep√§iv√§hoito osa-aikainen 12-13pv/kk palse
    ('c0f5cf0a-130e-11ed-b90a-7f619c57012f','d146f678-130d-11ed-a574-c39e98171347',daterange('2022-08-01', NULL, '[]'),82000,0.7,57400,82000,0.7,57400),
    ('8baf9264-3d6f-11ed-afc0-cb163b2ab442','d146f678-130d-11ed-a574-c39e98171347',daterange('2020-08-01', '2022-07-31', '[]'),77900,0.7,54500,77900,0.7,54500),
    ('15b7d6ca-3d7e-11ed-af30-27e5a0f5cd47','d146f678-130d-11ed-a574-c39e98171347',daterange('2018-08-01', '2020-07-31', '[]'),75300,0.7,52700,75300,0.7,52700),


    --07a1bde8-130e-11ed-a575-bbe09459e8d0	Yksityinen perhep√§iv√§hoito osa-aikainen 29h/viikko palse
    ('e0e7e924-130e-11ed-b90b-6be35ba0f06a','07a1bde8-130e-11ed-a575-bbe09459e8d0',daterange('2022-08-01', NULL, '[]'),82000,0.7,57400,82000,0.7,57400),
    ('a22af61e-3d6f-11ed-afc1-6f86988f6ea9','07a1bde8-130e-11ed-a575-bbe09459e8d0',daterange('2020-08-01', '2022-07-31', '[]'),77900,0.7,54500,77900,0.7,54500),
    ('63387652-3d7e-11ed-af31-432aaa1d53bf','07a1bde8-130e-11ed-a575-bbe09459e8d0',daterange('2018-08-01', '2020-07-31', '[]'),75300,0.7,52700,75300,0.7,52700),

    --5c8bdebc-3350-11ed-a1e8-23631d9473d4	Yksityinen perhep√§iv√§hoito osap√§iv√§ 4h palse
    ('bc2ebf9c-3350-11ed-a1e9-73702ec34944','5c8bdebc-3350-11ed-a1e8-23631d9473d4',daterange('2022-08-01', NULL, '[]'),82000,0.55,45100,82000,0.55,45100),
    ('bac6f8a8-3d6f-11ed-afc2-2bf0137ea510','5c8bdebc-3350-11ed-a1e8-23631d9473d4',daterange('2020-08-01', '2022-07-31', '[]'),77900,0.55,42900,77900,0.55,42900),
    ('8b7af72a-3d7e-11ed-af32-2331fa2a3e57','5c8bdebc-3350-11ed-a1e8-23631d9473d4',daterange('2018-08-01', '2020-07-31', '[]'),75300,0.55,41400,75300,0.55,41400),

    --86495b70-130d-11ed-a573-bfda992853b8	Yksityinen perhep√§iv√§hoito osaviikko 20h palse
    ('a2e8de44-130e-11ed-b909-4b7d84273804','86495b70-130d-11ed-a573-bfda992853b8',daterange('2022-08-01', NULL, '[]'),82000,0.55,45100,82000,0.55,45100),
    ('76a13b5c-3d6f-11ed-afbf-830e0a222ba5','86495b70-130d-11ed-a573-bfda992853b8',daterange('2020-08-01', '2022-07-31', '[]'),77900,0.55,42900,77900,0.55,42900),
    ('f6077af6-3d7d-11ed-af2f-17d04a90076d','86495b70-130d-11ed-a573-bfda992853b8',daterange('2018-08-01', '2020-07-31', '[]'),75300,0.55,41400,75300,0.55,41400),


    --c288a0c6-9ef2-11ec-853f-47b02ebd51bb	Yksityinen varhaiskasvatus palse
    ('a0699b70-1185-11ed-80ed-f73cd8514a0b','c288a0c6-9ef2-11ec-853f-47b02ebd51bb',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('49e5c21e-3d6e-11ed-afb5-57493fd49d78','c288a0c6-9ef2-11ec-853f-47b02ebd51bb',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('4ef6098c-3d7b-11ed-af25-fb5853405def','c288a0c6-9ef2-11ec-853f-47b02ebd51bb',daterange('2018-08-01', '2019-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('3efcf881-ea71-48e1-bd93-eb1b4134b34c','c288a0c6-9ef2-11ec-853f-47b02ebd51bb',daterange('2019-08-01', '2020-07-31', '[]'),79700,1.0,79700,126600,1.0,126600),

    -- 2022-08-01 -
    ('546fdfbe-123d-11ed-b5b1-9fd256edadac','de463972-9f97-11ec-a22c-931f5a294cea',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a068c5d8-1185-11ed-80ed-5bf569d0e21f','86ef70a0-bf85-11eb-91e6-1fb57a101161',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a06993e6-1185-11ed-80ed-4bc089b67986','503590f0-b961-11eb-b520-53740af3f7ee',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a0699788-1185-11ed-80ed-77eb3184d140','503591ae-b961-11eb-b521-1fca99358eef',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a0699990-1185-11ed-80ed-fb1b3ae80278','c288a058-9ef2-11ec-853e-9bcaa26ea729',daterange('2022-08-01', NULL, '[]'),86800,0.0,0,133200,0.0,0),
    ('70051c0e-130e-11ed-b908-0f4908e167de','19094188-130d-11ed-a572-473907d23b65',daterange('2022-08-01', NULL, '[]'),82000,1.0,82000,82000,1.0,82000),
    ('a0699d64-1185-11ed-80ed-334f889d313a','de463bd4-9f97-11ec-a22d-4791bc036bd9',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a0699f44-1185-11ed-80ed-6b21f4cc6fcd','86ef7370-bf85-11eb-91e7-6fcd728c518d',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069a1ec-1185-11ed-80ed-4bda5bf9eb9f','c2886f84-9ef2-11ec-8538-734ab2ac6c71',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069a7c8-1185-11ed-80ed-6b8aafd8acb1','c2889ef0-9ef2-11ec-853b-2f545a699ef0',daterange('2022-08-01', NULL, '[]'),86800,0.0,0,133200,0.0,0),
    ('a069b2ea-1185-11ed-80ed-ff1829825b94','de463c38-9f97-11ec-a22e-97907801ecdc',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069b556-1185-11ed-80ed-2f58ed6d6505','c288a152-9ef2-11ec-8540-2fc7b01eb49e',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069b7e0-1185-11ed-80ed-431a58630a1f','c288a26a-9ef2-11ec-8541-57b8edf0098f',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069bde4-1185-11ed-80ed-3338f4975589','de463c92-9f97-11ec-a22f-67d2f441c52f',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069c064-1185-11ed-80ed-3fda788db735','c288a33c-9ef2-11ec-8543-c7f61d98bbff',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069c3e8-1185-11ed-80ed-3b5a6394ef9c','2e0f93a8-e57b-11ec-a452-7f636f92b30c',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069c668-1185-11ed-80ed-eb17315610fc','c288a3aa-9ef2-11ec-8544-072366604c6f',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069c8e8-1185-11ed-80ed-eb42924cc304','c288a3fa-9ef2-11ec-8545-0feaf5efd1b9',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069cb72-1185-11ed-80ed-bf1fe485f058','c288a440-9ef2-11ec-8546-8f405f297780',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('7b21bdbc-4609-11ed-b6d3-37b8fa21d136','adff4b3e-4607-11ed-b4df-b338fa9c8022',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('228494b2-460a-11ed-b6d6-43fdbc6f7b30','274acbd0-4608-11ed-b4e0-07ecacd56b03',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('703b728e-460a-11ed-b6d9-cb73afa18797','4e62e91e-4608-11ed-b4e1-ab7b3db89639',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('0b4398d2-3f2c-11ed-aec6-2374da60e610','91a50d60-3f28-11ed-8217-b35ad6248e8d',daterange('2022-08-01', NULL, '[]'),86800,0.55,47740,133200,0.55,73260),
    ('a2ca6410-3f2c-11ed-aeca-6fcd7c353cce','bf4b4946-3f28-11ed-8218-0392ee6d4528',daterange('2022-08-01', NULL, '[]'),86800,0.7,60760,133200,0.7,93240),
    ('c9abd74a-4084-11ed-b945-d3c3ccafac87','99ab7baa-4083-11ed-8078-87d60cea9aea',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069d266-1185-11ed-80ed-7b2f4ae3b150','de463cec-9f97-11ec-a230-bb19e6bd6663',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069d536-1185-11ed-80ed-e7faea8ca17f','c288a4d6-9ef2-11ec-8548-53f49e3cb87b',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069da90-1185-11ed-80ed-235dab6d6307','c288a58a-9ef2-11ec-8549-2f93c47040bf',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069ddf6-1185-11ed-80ed-138314d2fb2e','c288a5d0-9ef2-11ec-854a-b33b00871752',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069e0c6-1185-11ed-80ed-0b3baa0b0f24','c288a620-9ef2-11ec-854b-13e81732636f',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069e2ce-1185-11ed-80ed-db4035b78004','c288a666-9ef2-11ec-854c-8bcc8b9a9307',daterange('2022-08-01', NULL, '[]'),18800,1.0,18800,18800,1.0,18800),
    ('a069e59e-1185-11ed-80ed-c3ad8e757594','de463d32-9f97-11ec-a231-5bcd9b48daeb',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069e788-1185-11ed-80ed-4b0ba562aaae','c288a6ac-9ef2-11ec-854d-8ba46873165a',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069e95e-1185-11ed-80ed-d7ecf75f0ca9','7dedd9a8-a04c-11ec-a72b-3779d97aac97',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069eb3e-1185-11ed-80ed-4773d0382645','c288a706-9ef2-11ec-854e-8fec0360d917',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069ed0a-1185-11ed-80ed-cbd05a0aef01','de463d82-9f97-11ec-a232-a782189e7c84',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069eee0-1185-11ed-80ed-770edea4ecf8','c288a74c-9ef2-11ec-854f-935955a88b27',daterange('2022-08-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    -- 2020-08-01 - 2022-07-31
    ('395e4a12-3d6c-11ed-afb0-c7bac1ba3290','de463972-9f97-11ec-a22c-931f5a294cea',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('74f7d3c6-3d6d-11ed-afb1-93f75df1e0fe','86ef70a0-bf85-11eb-91e6-1fb57a101161',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('db2abfe6-3d6d-11ed-afb2-372110f4492e','503590f0-b961-11eb-b520-53740af3f7ee',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('11e5c850-3d6e-11ed-afb3-73c8fde65200','503591ae-b961-11eb-b521-1fca99358eef',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('2b3b1a4e-3d6e-11ed-afb4-1b0366d2f424','c288a058-9ef2-11ec-853e-9bcaa26ea729',daterange('2020-08-01', '2022-07-31', '[]'),82500,0.0,0,126600,0.0,0),
    ('6640c166-3d6e-11ed-afb6-b354a4de12c5','19094188-130d-11ed-a572-473907d23b65',daterange('2020-08-01', '2022-07-31', '[]'),77900,1.0,77900,77900,1.0,77900),
    ('80874cc0-3d6e-11ed-afb7-1f1d7077bd84','de463bd4-9f97-11ec-a22d-4791bc036bd9',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('950610be-3d6e-11ed-afb8-0b3ab914f277','86ef7370-bf85-11eb-91e7-6fcd728c518d',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('df7b59e2-3d6e-11ed-afb9-f36186c18b41','c2886f84-9ef2-11ec-8538-734ab2ac6c71',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('2e96d06a-3d6f-11ed-afbc-af7eb9419839','c2889ef0-9ef2-11ec-853b-2f545a699ef0',daterange('2020-08-01', '2022-07-31', '[]'),82500,0.0,0,126600,0.0,0),
    ('cf0c912e-3d6f-11ed-afc3-17d3e503e767','de463c38-9f97-11ec-a22e-97907801ecdc',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('f26cb36a-3d6f-11ed-afc4-472ed7152d4f','c288a152-9ef2-11ec-8540-2fc7b01eb49e',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('077b8bfa-3d70-11ed-afc5-5b747879b16a','c288a26a-9ef2-11ec-8541-57b8edf0098f',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('34557d66-3d70-11ed-afc7-573b1713cafa','de463c92-9f97-11ec-a22f-67d2f441c52f',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('48abb488-3d70-11ed-afc8-5732f16c896d','c288a33c-9ef2-11ec-8543-c7f61d98bbff',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('68bbd960-3d70-11ed-afc9-b3b10c41656b','2e0f93a8-e57b-11ec-a452-7f636f92b30c',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('81cdb31a-3d70-11ed-afca-a7ae498a2d4e','c288a3aa-9ef2-11ec-8544-072366604c6f',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('955b02c0-3d70-11ed-afcb-570dde9dffee','c288a3fa-9ef2-11ec-8545-0feaf5efd1b9',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('a81c1b38-3d70-11ed-afcc-87e8057ec4db','c288a440-9ef2-11ec-8546-8f405f297780',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('a4acf638-4609-11ed-b6d4-f7d28059b1d3','adff4b3e-4607-11ed-b4df-b338fa9c8022',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('39345a3a-460a-11ed-b6d7-fb803fda28b4','274acbd0-4608-11ed-b4e0-07ecacd56b03',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('86f0f9cc-460a-11ed-b6da-8f9f52e10d8d','4e62e91e-4608-11ed-b4e1-ab7b3db89639',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('681a0456-3f2c-11ed-aec8-df57cb369c7b','91a50d60-3f28-11ed-8217-b35ad6248e8d',daterange('2020-08-01', '2022-07-31', '[]'),82500,0.55,45400,126600,0.55,69600),
    ('bfcab9ca-3f2c-11ed-aecb-6f46722c47f3','bf4b4946-3f28-11ed-8218-0392ee6d4528',daterange('2020-08-01', '2022-07-31', '[]'),82500,0.7,54530,126600,0.7,88600),
    ('f16de7be-4084-11ed-b946-5f350f214358','99ab7baa-4083-11ed-8078-87d60cea9aea',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('cf734fee-3d70-11ed-afce-4f697dd18b65','de463cec-9f97-11ec-a230-bb19e6bd6663',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('e70d55be-3d70-11ed-afcf-4fc110f7308c','c288a4d6-9ef2-11ec-8548-53f49e3cb87b',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('fa2770c6-3d70-11ed-afd0-6b3ba4a75d25','c288a58a-9ef2-11ec-8549-2f93c47040bf',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('124408a4-3d71-11ed-afd1-a35ea68ab6f6','c288a5d0-9ef2-11ec-854a-b33b00871752',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('273c5b3a-3d71-11ed-afd2-9b7277c2d367','c288a620-9ef2-11ec-854b-13e81732636f',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('3fcff328-3d71-11ed-afd3-5b2c9f5b1d57','c288a666-9ef2-11ec-854c-8bcc8b9a9307',daterange('2020-08-01', '2022-07-31', '[]'),17900,1.0,17900,17900,1.0,17900),
    ('56de1112-3d71-11ed-afd4-bb00225ed926','de463d32-9f97-11ec-a231-5bcd9b48daeb',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('6f300270-3d71-11ed-afd5-c3ef7f7400cd','c288a6ac-9ef2-11ec-854d-8ba46873165a',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('82eab030-3d71-11ed-afd6-bbdd74c070a8','7dedd9a8-a04c-11ec-a72b-3779d97aac97',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('9e0ab3e2-3d71-11ed-afd7-9bae9eb93fb6','c288a706-9ef2-11ec-854e-8fec0360d917',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('b37df52c-3d71-11ed-afd8-eb19128910e9','de463d82-9f97-11ec-a232-a782189e7c84',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    ('cbc424c6-3d71-11ed-afd9-7721e5eeb1f6','c288a74c-9ef2-11ec-854f-935955a88b27',daterange('2020-08-01', '2022-07-31', '[]'),82500,1.0,82500,126600,1.0,126600),
    -- 2018-08-01 - 2020-07-31
    ('d1a95948-3d7a-11ed-af20-0b7ad585c330','de463972-9f97-11ec-a22c-931f5a294cea',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('ee6291c6-3d7a-11ed-af21-f7fa70164b0e','86ef70a0-bf85-11eb-91e6-1fb57a101161',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('065defaa-3d7b-11ed-af22-0bcd4cc2bb5d','503590f0-b961-11eb-b520-53740af3f7ee',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('1d10b124-3d7b-11ed-af23-b398dc699b3f','503591ae-b961-11eb-b521-1fca99358eef',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('340ee706-3d7b-11ed-af24-eb32787a9689','c288a058-9ef2-11ec-853e-9bcaa26ea729',daterange('2018-08-01', '2020-07-31', '[]'),72500,0.0,0,122300,0.0,0),
    ('6a4df9ba-3d7b-11ed-af26-075e6bfe6583','19094188-130d-11ed-a572-473907d23b65',daterange('2018-08-01', '2020-07-31', '[]'),75300,1.0,75300,75300,1.0,75300),
    ('83da47b2-3d7b-11ed-af27-a32dc43e1eb5','de463bd4-9f97-11ec-a22d-4791bc036bd9',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('98905b10-3d7b-11ed-af28-ef37142e55bb','86ef7370-bf85-11eb-91e7-6fcd728c518d',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('ad0a23dc-3d7b-11ed-af29-03beb8f92eb4','c2886f84-9ef2-11ec-8538-734ab2ac6c71',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('5efc2eea-3d7d-11ed-af2c-8bf4cf283c35','c2889ef0-9ef2-11ec-853b-2f545a699ef0',daterange('2018-08-01', '2020-07-31', '[]'),72500,0.0,0,122300,0.0,0),
    ('b08b4326-3d7e-11ed-af33-c3745d951b56','de463c38-9f97-11ec-a22e-97907801ecdc',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('d89dc9e2-3d7e-11ed-af34-bbfd186d0b44','c288a152-9ef2-11ec-8540-2fc7b01eb49e',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('03e51916-3d7f-11ed-af35-5bbcc255f3e5','c288a26a-9ef2-11ec-8541-57b8edf0098f',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('4f9422d4-3d80-11ed-af37-af4f471c1d86','de463c92-9f97-11ec-a22f-67d2f441c52f',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('68dda6b6-3d80-11ed-af38-cf54c3493635','c288a33c-9ef2-11ec-8543-c7f61d98bbff',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('8d607c84-3d80-11ed-af39-4fe0653599aa','2e0f93a8-e57b-11ec-a452-7f636f92b30c',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('a6e3358e-3d80-11ed-af3a-a35daaa50fb2','c288a3aa-9ef2-11ec-8544-072366604c6f',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('13da97fe-3d81-11ed-af3b-173c24efde2a','c288a3fa-9ef2-11ec-8545-0feaf5efd1b9',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('2ae0b398-3d81-11ed-af3c-2b0905961944','c288a440-9ef2-11ec-8546-8f405f297780',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('be8a3d86-4609-11ed-b6d5-936777c8c994','adff4b3e-4607-11ed-b4df-b338fa9c8022',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('4cf1607c-460a-11ed-b6d8-e773b086ea27','274acbd0-4608-11ed-b4e0-07ecacd56b03',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('9a78eef0-460a-11ed-b6db-df5b0a0bdf0e','4e62e91e-4608-11ed-b4e1-ab7b3db89639',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('835dc9e6-3f2c-11ed-aec9-03e2870650b1','91a50d60-3f28-11ed-8217-b35ad6248e8d',daterange('2018-08-01', '2020-07-31', '[]'),72500,0.55,39900,122300,0.55,67300),
    ('d5b19984-3f2c-11ed-aecc-7b0ae92655df','bf4b4946-3f28-11ed-8218-0392ee6d4528',daterange('2018-08-01', '2020-07-31', '[]'),72500,0.7,50800,122300,0.7,85600),
    ('09976d2e-4085-11ed-b947-af0854950407','99ab7baa-4083-11ed-8078-87d60cea9aea',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('586b7d02-3d81-11ed-af3e-e32f81c28df5','de463cec-9f97-11ec-a230-bb19e6bd6663',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('6af55d12-3d81-11ed-af3f-378378102e94','c288a4d6-9ef2-11ec-8548-53f49e3cb87b',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('81a4734a-3d81-11ed-af40-2782b5239e1a','c288a58a-9ef2-11ec-8549-2f93c47040bf',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('c252ef20-3d81-11ed-af41-b71c60a378d1','c288a5d0-9ef2-11ec-854a-b33b00871752',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('02e59ad8-3d82-11ed-af42-3f8c4027071e','c288a620-9ef2-11ec-854b-13e81732636f',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('17745214-3d82-11ed-af43-bfba1b0ea5a9','c288a666-9ef2-11ec-854c-8bcc8b9a9307',daterange('2018-08-01', '2020-07-31', '[]'),17300,1.0,17300,17300,1.0,17300),
    ('2c2f4876-3d82-11ed-af44-63eee7048597','de463d32-9f97-11ec-a231-5bcd9b48daeb',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('45714ab4-3d82-11ed-af45-8f5b17cd0b00','c288a6ac-9ef2-11ec-854d-8ba46873165a',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('58b3577a-3d82-11ed-af46-b7ef5bf4e366','7dedd9a8-a04c-11ec-a72b-3779d97aac97',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('6d15d562-3d82-11ed-af47-bbe791fc1846','c288a706-9ef2-11ec-854e-8fec0360d917',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('d4ff3e66-3d82-11ed-af48-9f1e445c1132','de463d82-9f97-11ec-a232-a782189e7c84',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300),
    ('eb482480-3d82-11ed-af49-2b2935f561b6','c288a74c-9ef2-11ec-854f-935955a88b27',daterange('2018-08-01', '2020-07-31', '[]'),72500,1.0,72500,122300,1.0,122300)

ON CONFLICT (id) DO
UPDATE SET
    validity = EXCLUDED.validity,
    base_value = EXCLUDED.base_value,
    coefficient = EXCLUDED.coefficient,
    value = EXCLUDED.value,
    base_value_under_3y = EXCLUDED.base_value_under_3y,
    coefficient_under_3y = EXCLUDED.coefficient_under_3y,
    value_under_3y = EXCLUDED.value_under_3y
WHERE
    service_need_option_voucher_value.validity <> EXCLUDED.validity OR
    service_need_option_voucher_value.base_value <> EXCLUDED.base_value OR
    service_need_option_voucher_value.coefficient <> EXCLUDED.coefficient OR
    service_need_option_voucher_value.value <> EXCLUDED.value OR
    service_need_option_voucher_value.base_value_under_3y <> EXCLUDED.base_value_under_3y OR
    service_need_option_voucher_value.coefficient_under_3y <> EXCLUDED.coefficient_under_3y OR
    service_need_option_voucher_value.value_under_3y <> EXCLUDED.value_under_3y;
