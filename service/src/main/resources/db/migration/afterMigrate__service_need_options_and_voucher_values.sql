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
    ('c288a058-9ef2-11ec-853e-9bcaa26ea729','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','Private early childhood education',NULL,'DAYCARE',FALSE,1.0,1.0,40,FALSE,FALSE,'Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus', 4, 1.75, true),
    ('c288a0c6-9ef2-11ec-853f-47b02ebd51bb','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','Private early childhood education service voucher',NULL,'DAYCARE',FALSE,1.0,1.0,40,FALSE,FALSE,'Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse', 5,1.75, false),
    ('19094188-130d-11ed-a572-473907d23b65','Yksityinen perhepäivähoito palse','Yksityinen perhepäivähoito palse','Private family daycare service voucher',NULL,'DAYCARE',FALSE,1.0,1.0,40,FALSE,FALSE,'Yksityinen perhepäivähoito palse','Yksityinen perhepäivähoito palse','Yksityinen perhepäivähoito palse','Yksityinen perhepäivähoito palse', 6,1, false),

    -- DAYCARE_PART_TIME
    ('de463bd4-9f97-11ec-a22d-4791bc036bd9', 'Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Early childhood education under 5-h day',NULL,'DAYCARE_PART_TIME',TRUE,0.6,0.54,25,FALSE,FALSE,'Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä', null,1.75, true),
    ('86ef7370-bf85-11eb-91e7-6fcd728c518d','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Early childhood education under 5-h day',NULL,'DAYCARE_PART_TIME',FALSE,0.6,0.54,25,FALSE,FALSE,'Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä', 1000,1.75, true),
    ('c2886f84-9ef2-11ec-8538-734ab2ac6c71','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','Early childhood education under 7-h day',NULL,'DAYCARE_PART_TIME',FALSE,0.75,1.0,35,FALSE,FALSE,'Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä', 1003,1.75, true),
    ('c2886fca-9ef2-11ec-8539-1b9652921b6d','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','Private part time 12-13d/m service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.65,1.0,26.3,FALSE,FALSE,'Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse', null,1.75, false),
    ('c2889e1e-9ef2-11ec-853a-aff6768857cb','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','Private part time 29h/week service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.6,1.0,29,FALSE,FALSE,'Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse', null,1.75, false),
    ('c2889ef0-9ef2-11ec-853b-2f545a699ef0','Yksityinen osapäivä','Yksityinen osapäivä','Private part time',NULL,'DAYCARE_PART_TIME',FALSE,0.5,0.54,20,TRUE,FALSE,'Yksityinen osapäivä','Yksityinen osapäivä','Yksityinen osapäivä','Yksityinen osapäivä', null,1.75, true),
    ('c2889f72-9ef2-11ec-853c-3bc6a35d44a3','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','Private part time 4h service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.5,0.54,20,TRUE,FALSE,'Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse', null,1.75, false),
    ('50359334-b961-11eb-b525-f3febdfea5d3','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','Private part time 20h service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.5,0.54,20,FALSE,TRUE,'Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse', null,1.75, false),
    ('86495b70-130d-11ed-a573-bfda992853b8','Yksityinen perhepäivähoito osaviikko 20h palse','Yksityinen perhepäivähoito osaviikko 20h palse','Private family daycare part time 20h service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.55,0.54,20,FALSE,TRUE,'Yksityinen perhepäivähoito osaviikko 20h palse','Yksityinen perhepäivähoito osaviikko 20h palse','Yksityinen perhepäivähoito osaviikko 20h palse','Yksityinen perhepäivähoito osaviikko 20h palse', null,1, false),
    ('d146f678-130d-11ed-a574-c39e98171347','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Private family daycare part time 12-13d/m service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.7,1.0,26.3,FALSE,FALSE,'Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse','Yksityinen perhepäivähoito osa-aikainen 12-13pv/kk palse', null,1, false),
    ('07a1bde8-130e-11ed-a575-bbe09459e8d0','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Private family daycare part time 29h/week service voucher',NULL,'DAYCARE_PART_TIME',FALSE,0.7,1.0,29,FALSE,FALSE,'Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse','Yksityinen perhepäivähoito osa-aikainen 29h/viikko palse', null,1, false),

    -- PRESCHOOL
    ('de463c38-9f97-11ec-a22e-97907801ecdc', 'Esiopetus 4h ','Esiopetus 4h ','Pre-school education 4h ',NULL,'PRESCHOOL',TRUE,0,0.54,20,TRUE,FALSE,'Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h', null,1.75, true),
    ('c288a152-9ef2-11ec-8540-2fc7b01eb49e','Esiopetus 4h ','Esiopetus 4h ','Pre-school education 4h ',NULL,'PRESCHOOL',FALSE,0,0.54,20,TRUE,FALSE,'Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h', 2000,1.75, true),
    ('c288a26a-9ef2-11ec-8541-57b8edf0098f','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','A two-year pre-school trial',NULL,'PRESCHOOL',FALSE,0,0.54,20,TRUE,FALSE,'Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu', null,1.75, true),
    ('c288a2d8-9ef2-11ec-8542-231141d61e61','Yksityinen esiopetus 4 h palse','Yksityinen esiopetus 4 h palse','Private pre-school education 4 h service voucher',NULL,'PRESCHOOL',FALSE,0.55,0.54,20,TRUE,FALSE,'Yksityinen esiopetus 4 h palse','Yksityinen esioeptus 4 h palse','Yksityinen esioeptus 4 h palse','Yksityinen esioeptus 4 h palse', null,1.75, false),

    -- PRESCHOOL_DAYCARE
    ('de463c92-9f97-11ec-a22f-67d2f441c52f','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Pre-school education + early childhood education',NULL,'PRESCHOOL_DAYCARE',TRUE,0.6,0.46,25,FALSE,FALSE,'Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus', null,1.75, true),
    ('c288a33c-9ef2-11ec-8543-c7f61d98bbff','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Pre-school education + early childhood education',NULL,'PRESCHOOL_DAYCARE',FALSE,0.6,0.46,25,FALSE,FALSE,'Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus', null,1.75, true),
    ('2e0f93a8-e57b-11ec-a452-7f636f92b30c','Esiopetus + varhaiskasvatus 2v','Esiopetus + varhaiskasvatus 2v','Pre-school education + early childhood education 2y',NULL,'PRESCHOOL_DAYCARE',FALSE,0.6,0.46,25,FALSE,FALSE,'Esiopetus + varhaiskasvatus 2v','Esiopetus + varhaiskasvatus 2v','Esiopetus + varhaiskasvatus 2v','Esiopetus + varhaiskasvatus 2v', null,1.75, true),
    ('c288a3aa-9ef2-11ec-8544-072366604c6f','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','Pre-school education + early childhood education less than 3h',NULL,'PRESCHOOL_DAYCARE',FALSE,0.45,0.46,20,FALSE,FALSE,'Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h', null,1.75, true),
    ('c288a3fa-9ef2-11ec-8545-0feaf5efd1b9','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','Pre-school education + early childhood education 10 d/m',10,'PRESCHOOL_DAYCARE',FALSE,0.3,0.46,9.5,FALSE,FALSE,'Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk', null,1.75, true),
    ('c288a440-9ef2-11ec-8546-8f405f297780','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','Pre-school education + early childhood education 13 d/m',13,'PRESCHOOL_DAYCARE',FALSE,0.45,0.46,12.4,FALSE,FALSE,'Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk', null,1.75, true),
    ('c288a490-9ef2-11ec-8547-ab2d0b032c1c','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','Private pre-school education + early childhood education service voucher',NULL,'PRESCHOOL_DAYCARE',FALSE,0.6,1.0,25,FALSE,FALSE,'Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse', null,1.75, false),

    -- CLUB
    ('de463cec-9f97-11ec-a230-bb19e6bd6663','Kerho 2 x viikko','Kerho 2 x viikko','Club 2 x week',NULL,'CLUB',TRUE,0,1.0,10,FALSE,FALSE,'Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko', null,1.75, true),
    ('c288a4d6-9ef2-11ec-8548-53f49e3cb87b','Kerho 2 x viikko','Kerho 2 x viikko','Club 2 x week',NULL,'CLUB',FALSE,0,1.0,10,FALSE,FALSE,'Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko', null,1.75, true),
    ('c288a58a-9ef2-11ec-8549-2f93c47040bf','Kerho 3 x viikko','Kerho 3 x viikko','Club 3 x week',NULL,'CLUB',FALSE,0,1.0,10,FALSE,FALSE,'Kerho 3 x viikko','Kerho 3 x viikko','Kerho 3 x viikko','Kerho 3 x viikko', null,1.75, true),
    ('c288a5d0-9ef2-11ec-854a-b33b00871752','Perhekerho','Perhekerho','Family club',NULL,'CLUB',FALSE,0,1.0,10,FALSE,FALSE,'Perhekerho','Perhekerho','Perhekerho','Perhekerho', null,1.75, true),
    ('c288a620-9ef2-11ec-854b-13e81732636f','Tilapäinen kerho','Tilapäinen kerho','Temporary club',NULL,'CLUB',FALSE,0,1.0,10,FALSE,FALSE,'Tilapäinen kerho','Tilapäinen kerho','Tilapäinen kerho','Tilapäinen kerho', null,1.75, true),
    ('c288a666-9ef2-11ec-854c-8bcc8b9a9307','Yksityinen kerho palse','Yksityinen kerho palse','Private club service voucher',NULL,'CLUB',FALSE,1.0,1.0,10,FALSE,FALSE,'Yksityinen kerho palse','Yksityinen kerho palse','Yksityinen kerho palse','Yksityinen kerho palse', null,1.75, false),

    -- TEMPORARY_DAYCARE
    ('de463d32-9f97-11ec-a231-5bcd9b48daeb', 'Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Temporary early childhood education',NULL,'TEMPORARY_DAYCARE',TRUE,1.0,0.54,40,FALSE,FALSE,'Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus', null,1.75, true),
    ('c288a6ac-9ef2-11ec-854d-8ba46873165a','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Temporary early childhood education',NULL,'TEMPORARY_DAYCARE',FALSE,1.0,0.54,40,FALSE,FALSE,'Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus', null,1.75, true),

    -- TEMPORARY_DAYCARE_PART_DAY
    ('7dedd9a8-a04c-11ec-a72b-3779d97aac97','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','temporary part-time early childhood education',NULL,'TEMPORARY_DAYCARE_PART_DAY',TRUE,1.0,1.0,20,TRUE,FALSE,'Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus', null,1.75, true),
    ('c288a706-9ef2-11ec-854e-8fec0360d917','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','temporary part-time early childhood education',NULL,'TEMPORARY_DAYCARE_PART_DAY',FALSE,1.0,1.0,20,TRUE,FALSE,'Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus', null,1.75, true),

    -- PREPARATORY
    ('de463d82-9f97-11ec-a232-a782189e7c84', 'Valmistava esiopetus','Valmistava esiopetus','Preparatory pre-school education',NULL,'PREPARATORY',TRUE,0,0.54,25,TRUE,FALSE,'Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus', null,1.75, true),
    ('c288a74c-9ef2-11ec-854f-935955a88b27','Valmistava esiopetus','Valmistava esiopetus','Preparatory pre-school education',NULL,'PREPARATORY',FALSE,0,0.54,25,TRUE,FALSE,'Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus', null,1.75, true),

    -- PREPARATORY_DAYCARE
    ('05999cd6-1fa3-11ed-b545-8f2c5e506764','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Preparatory pre-school education + early childhood education',NULL,'PREPARATORY_DAYCARE',TRUE,0.6,0.54,25,TRUE,FALSE,'Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus', null,1.75, true),
    ('53bad240-1fa3-11ed-b546-eb62f017d64a','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Preparatory pre-school education + early childhood education',NULL,'PREPARATORY_DAYCARE',FALSE,0.6,0.54,25,TRUE,FALSE,'Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus','Valmistava esiopetus + varhaiskasvatus', null,1.75, true)


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
    ('546fdfbe-123d-11ed-b5b1-9fd256edadac','de463972-9f97-11ec-a22c-931f5a294cea',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a068c5d8-1185-11ed-80ed-5bf569d0e21f','86ef70a0-bf85-11eb-91e6-1fb57a101161',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a06993e6-1185-11ed-80ed-4bc089b67986','503590f0-b961-11eb-b520-53740af3f7ee',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a0699788-1185-11ed-80ed-77eb3184d140','503591ae-b961-11eb-b521-1fca99358eef',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a0699990-1185-11ed-80ed-fb1b3ae80278','c288a058-9ef2-11ec-853e-9bcaa26ea729',daterange('2000-01-01', NULL, '[]'),86800,0.0,0,133200,0.0,0),
    ('a0699b70-1185-11ed-80ed-f73cd8514a0b','c288a0c6-9ef2-11ec-853f-47b02ebd51bb',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('70051c0e-130e-11ed-b908-0f4908e167de','19094188-130d-11ed-a572-473907d23b65',daterange('2000-01-01', NULL, '[]'),82000,1.0,82000,82000,1.0,82000),
    ('a0699d64-1185-11ed-80ed-334f889d313a','de463bd4-9f97-11ec-a22d-4791bc036bd9',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a0699f44-1185-11ed-80ed-6b21f4cc6fcd','86ef7370-bf85-11eb-91e7-6fcd728c518d',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069a1ec-1185-11ed-80ed-4bda5bf9eb9f','c2886f84-9ef2-11ec-8538-734ab2ac6c71',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069a408-1185-11ed-80ed-5bee3b23fe54','c2886fca-9ef2-11ec-8539-1b9652921b6d',daterange('2000-01-01', NULL, '[]'),86800,0.7,60760,133200,0.7,93240),
    ('a069a5f2-1185-11ed-80ed-b7c85eff5571','c2889e1e-9ef2-11ec-853a-aff6768857cb',daterange('2000-01-01', NULL, '[]'),86800,0.7,60760,133200,0.7,93240),
    ('a069a7c8-1185-11ed-80ed-6b8aafd8acb1','c2889ef0-9ef2-11ec-853b-2f545a699ef0',daterange('2000-01-01', NULL, '[]'),86800,0.0,0,133200,0.0,0),
    ('a069aa98-1185-11ed-80ed-0f06e4c634d7','c2889f72-9ef2-11ec-853c-3bc6a35d44a3',daterange('2000-01-01', NULL, '[]'),86800,0.55,47740,133200,0.55,73260),
    ('a069afc0-1185-11ed-80ed-53c4aa412615','50359334-b961-11eb-b525-f3febdfea5d3',daterange('2000-01-01', NULL, '[]'),86800,0.55,47740,133200,0.55,73260),
    ('a2e8de44-130e-11ed-b909-4b7d84273804','86495b70-130d-11ed-a573-bfda992853b8',daterange('2000-01-01', NULL, '[]'),82000,0.55,45100,82000,0.55,45100),
    ('c0f5cf0a-130e-11ed-b90a-7f619c57012f','d146f678-130d-11ed-a574-c39e98171347',daterange('2000-01-01', NULL, '[]'),82000,0.7,57400,82000,0.7,57400),
    ('e0e7e924-130e-11ed-b90b-6be35ba0f06a','07a1bde8-130e-11ed-a575-bbe09459e8d0',daterange('2000-01-01', NULL, '[]'),82000,0.7,57400,82000,0.7,57400),
    ('a069b2ea-1185-11ed-80ed-ff1829825b94','de463c38-9f97-11ec-a22e-97907801ecdc',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069b556-1185-11ed-80ed-2f58ed6d6505','c288a152-9ef2-11ec-8540-2fc7b01eb49e',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069b7e0-1185-11ed-80ed-431a58630a1f','c288a26a-9ef2-11ec-8541-57b8edf0098f',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069ba6a-1185-11ed-80ed-9fe0cbbc284c','c288a2d8-9ef2-11ec-8542-231141d61e61',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069bde4-1185-11ed-80ed-3338f4975589','de463c92-9f97-11ec-a22f-67d2f441c52f',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069c064-1185-11ed-80ed-3fda788db735','c288a33c-9ef2-11ec-8543-c7f61d98bbff',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069c3e8-1185-11ed-80ed-3b5a6394ef9c','2e0f93a8-e57b-11ec-a452-7f636f92b30c',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069c668-1185-11ed-80ed-eb17315610fc','c288a3aa-9ef2-11ec-8544-072366604c6f',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069c8e8-1185-11ed-80ed-eb42924cc304','c288a3fa-9ef2-11ec-8545-0feaf5efd1b9',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069cb72-1185-11ed-80ed-bf1fe485f058','c288a440-9ef2-11ec-8546-8f405f297780',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069ce10-1185-11ed-80ed-4366a9322b9a','c288a490-9ef2-11ec-8547-ab2d0b032c1c',daterange('2000-01-01', NULL, '[]'),86800,0.5,41250,133200,0.5,63300),
    ('a069d266-1185-11ed-80ed-7b2f4ae3b150','de463cec-9f97-11ec-a230-bb19e6bd6663',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069d536-1185-11ed-80ed-e7faea8ca17f','c288a4d6-9ef2-11ec-8548-53f49e3cb87b',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069da90-1185-11ed-80ed-235dab6d6307','c288a58a-9ef2-11ec-8549-2f93c47040bf',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069ddf6-1185-11ed-80ed-138314d2fb2e','c288a5d0-9ef2-11ec-854a-b33b00871752',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069e0c6-1185-11ed-80ed-0b3baa0b0f24','c288a620-9ef2-11ec-854b-13e81732636f',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069e2ce-1185-11ed-80ed-db4035b78004','c288a666-9ef2-11ec-854c-8bcc8b9a9307',daterange('2000-01-01', NULL, '[]'),18800,1.0,18800,18800,1.0,18800),
    ('a069e59e-1185-11ed-80ed-c3ad8e757594','de463d32-9f97-11ec-a231-5bcd9b48daeb',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069e788-1185-11ed-80ed-4b0ba562aaae','c288a6ac-9ef2-11ec-854d-8ba46873165a',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069e95e-1185-11ed-80ed-d7ecf75f0ca9','7dedd9a8-a04c-11ec-a72b-3779d97aac97',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069eb3e-1185-11ed-80ed-4773d0382645','c288a706-9ef2-11ec-854e-8fec0360d917',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069ed0a-1185-11ed-80ed-cbd05a0aef01','de463d82-9f97-11ec-a232-a782189e7c84',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200),
    ('a069eee0-1185-11ed-80ed-770edea4ecf8','c288a74c-9ef2-11ec-854f-935955a88b27',daterange('2000-01-01', NULL, '[]'),86800,1.0,86800,133200,1.0,133200)

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
