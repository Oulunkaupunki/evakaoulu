-- SPDX-FileCopyrightText: 2021 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

INSERT INTO service_need_option
    (id, name_fi, name_sv, name_en, valid_placement_type, default_option, fee_coefficient, voucher_value_coefficient, occupancy_coefficient, daycare_hours_per_week, part_day, part_week, fee_description_fi, fee_description_sv, voucher_value_description_fi, voucher_value_description_sv, display_order)
VALUES
    -- DAYCARE
    ('de463972-9f97-11ec-a22c-931f5a294cea','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','DAYCARE',TRUE,1.0,1.0,1.0,40,FALSE,FALSE,'Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus', null),
    ('86ef70a0-bf85-11eb-91e6-1fb57a101161','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','DAYCARE',FALSE,1.0,1.0,1.0,40,FALSE,FALSE,'Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus','Kokoaikainen varhaiskasvatus', 1),
    ('503590f0-b961-11eb-b520-53740af3f7ee','Kokoaikainen varhaiskasvatus 10 pv/kk','Kokoaikainen varhaiskasvatus 10 pv/kk','Kokoaikainen varhaiskasvatus 10 pv/kk','DAYCARE',FALSE,0.5,1.0,0.54,20.2,FALSE,FALSE,'Kokoaikainen varhaiskasvatus 10 pv/kk','Kokoaikainen varhaiskasvatus 10 pv/kk','Kokoaikainen varhaiskasvatus 10 pv/kk','Kokoaikainen varhaiskasvatus 10 pv/kk', 2),
    ('503591ae-b961-11eb-b521-1fca99358eef','Kokoaikainen varhaiskasvatus 13 pv/kk','Kokoaikainen varhaiskasvatus 13 pv/kk','Kokoaikainen varhaiskasvatus 13 pv/kk','DAYCARE',FALSE,0.75,1.0,0.77,26.3,FALSE,FALSE,'Kokoaikainen varhaiskasvatus 13 pv/kk','Kokoaikainen varhaiskasvatus 13 pv/kk','Kokoaikainen varhaiskasvatus 13 pv/kk','Kokoaikainen varhaiskasvatus 13 pv/kk', 3),
    ('c288a058-9ef2-11ec-853e-9bcaa26ea729','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','DAYCARE',FALSE,1.0,0,1.0,40,FALSE,FALSE,'Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus','Yksityinen varhaiskasvatus', 4),
    ('c288a0c6-9ef2-11ec-853f-47b02ebd51bb','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','DAYCARE',FALSE,1.0,1.0,1.0,40,FALSE,FALSE,'Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse','Yksityinen varhaiskasvatus palse', 5),

    -- DAYCARE_PART_TIME
    ('de463bd4-9f97-11ec-a22d-4791bc036bd9', 'Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','DAYCARE_PART_TIME',TRUE,0.6,1.0,0.54,25,FALSE,FALSE,'Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä', null),
    ('86ef7370-bf85-11eb-91e7-6fcd728c518d','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','DAYCARE_PART_TIME',FALSE,0.6,1.0,0.54,25,FALSE,FALSE,'Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä','Varhaiskasvatus alle 5-h päivä', 1000),
    ('c2886f84-9ef2-11ec-8538-734ab2ac6c71','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','DAYCARE_PART_TIME',FALSE,0.75,1.0,1.0,35,FALSE,FALSE,'Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä','Varhaiskasvatus alle 7-h päivä', 1003),
    ('c2886fca-9ef2-11ec-8539-1b9652921b6d','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','DAYCARE_PART_TIME',FALSE,1.0,0.7,1.0,26.3,FALSE,FALSE,'Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse','Yksityinen osa-aikainen 12-13pv/kk palse', null),
    ('c2889e1e-9ef2-11ec-853a-aff6768857cb','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','DAYCARE_PART_TIME',FALSE,1.0,0.7,1.0,29,FALSE,FALSE,'Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse','Yksityinen osa-aikainen 29h/viikko palse', null),
    ('c2889ef0-9ef2-11ec-853b-2f545a699ef0','Yksityinen osapäivä','Yksityinen osapäivä','Yksityinen osapäivä','DAYCARE_PART_TIME',FALSE,1.0,0,0.54,20,TRUE,FALSE,'Yksityinen osapäivä','Yksityinen osapäivä','Yksityinen osapäivä','Yksityinen osapäivä', null),
    ('c2889f72-9ef2-11ec-853c-3bc6a35d44a3','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','DAYCARE_PART_TIME',FALSE,0.5,0.55,0.54,20,TRUE,FALSE,'Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse','Yksityinen osapäivä 4h palse', null),
    ('50359334-b961-11eb-b525-f3febdfea5d3','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','DAYCARE_PART_TIME',FALSE,1.0,0.55,0.54,20,FALSE,TRUE,'Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse','Yksityinen osaviikko 20h palse', null),

    -- PRESCHOOL
    ('de463c38-9f97-11ec-a22e-97907801ecdc', 'Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h ','PRESCHOOL',TRUE,0,1.0,0.54,20,TRUE,FALSE,'Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h', null),
    ('c288a152-9ef2-11ec-8540-2fc7b01eb49e','Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h ','PRESCHOOL',FALSE,0,1.0,0.54,20,TRUE,FALSE,'Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h ','Esiopetus 4h', 2000),
    ('c288a26a-9ef2-11ec-8541-57b8edf0098f','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','PRESCHOOL',FALSE,0,1.0,0.54,20,TRUE,FALSE,'Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu','Kaksivuotinen esiopetuskokeilu', null),
    ('c288a2d8-9ef2-11ec-8542-231141d61e61','Yksityinen esiopetus 4 h palse','Yksityinen esiopetus 4 h palse','Yksityinen esiopetus 4 h palse','PRESCHOOL',FALSE,1.0,1.0,0.54,20,TRUE,FALSE,'Yksityinen esiopetus 4 h palse','Yksityinen esioeptus 4 h palse','Yksityinen esioeptus 4 h palse','Yksityinen esioeptus 4 h palse', null),

    -- PRESCHOOL_DAYCARE
    ('de463c92-9f97-11ec-a22f-67d2f441c52f','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','PRESCHOOL_DAYCARE',TRUE,0.6,1.0,0.46,25,FALSE,FALSE,'Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus', null),
    ('c288a33c-9ef2-11ec-8543-c7f61d98bbff','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','PRESCHOOL_DAYCARE',FALSE,0.6,1.0,0.46,25,FALSE,FALSE,'Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus','Esiopetus + varhaiskasvatus', null),
    ('c288a3aa-9ef2-11ec-8544-072366604c6f','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','PRESCHOOL_DAYCARE',FALSE,0.45,1.0,0.46,20,FALSE,FALSE,'Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h','Esiopetus + varhaiskasvatus alle 3h', null),
    ('c288a3fa-9ef2-11ec-8545-0feaf5efd1b9','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','PRESCHOOL_DAYCARE',FALSE,0.3,1.0,0.46,9.5,FALSE,FALSE,'Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk','Esiopetus + varhaiskasvatus 10 pv/kk', null),
    ('c288a440-9ef2-11ec-8546-8f405f297780','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','PRESCHOOL_DAYCARE',FALSE,0.45,1.0,0.46,12.4,FALSE,FALSE,'Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk','Esiopetus + varhaiskasvatus 13 pv/kk', null),
    ('c288a490-9ef2-11ec-8547-ab2d0b032c1c','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','PRESCHOOL_DAYCARE',FALSE,0.5,0.5,1.0,25,FALSE,FALSE,'Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse','Yksityinen esiopetus + varhaiskasvatus palse', null),

    -- CLUB
    ('de463cec-9f97-11ec-a230-bb19e6bd6663','Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko','CLUB',TRUE,0,1.0,1.0,10,FALSE,FALSE,'Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko', null),
    ('c288a4d6-9ef2-11ec-8548-53f49e3cb87b','Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko','CLUB',FALSE,0,1.0,1.0,10,FALSE,FALSE,'Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko','Kerho 2 x viikko', null),
    ('c288a58a-9ef2-11ec-8549-2f93c47040bf','Kerho 3 x viikko','Kerho 3 x viikko','Kerho 3 x viikko','CLUB',FALSE,0,1.0,1.0,10,FALSE,FALSE,'Kerho 3 x viikko','Kerho 3 x viikko','Kerho 3 x viikko','Kerho 3 x viikko', null),
    ('c288a5d0-9ef2-11ec-854a-b33b00871752','Perhekerho','Perhekerho','Perhekerho','CLUB',FALSE,0,1.0,1.0,10,FALSE,FALSE,'Perhekerho','Perhekerho','Perhekerho','Perhekerho', null),
    ('c288a620-9ef2-11ec-854b-13e81732636f','Tilapäinen kerho','Tilapäinen kerho','Tilapäinen kerho','CLUB',FALSE,0,1.0,1.0,10,FALSE,FALSE,'Tilapäinen kerho','Tilapäinen kerho','Tilapäinen kerho','Tilapäinen kerho', null),
    ('c288a666-9ef2-11ec-854c-8bcc8b9a9307','Yksityinen kerho palse','Yksityinen kerho palse','Yksityinen kerho palse','CLUB',FALSE,1.0,1.0,1.0,10,FALSE,FALSE,'Yksityinen kerho palse','Yksityinen kerho palse','Yksityinen kerho palse','Yksityinen kerho palse', null),

    -- TEMPORARY_DAYCARE
    ('de463d32-9f97-11ec-a231-5bcd9b48daeb', 'Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','TEMPORARY_DAYCARE',TRUE,1.0,1.0,0.54,40,FALSE,FALSE,'Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus', null),
    ('c288a6ac-9ef2-11ec-854d-8ba46873165a','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','TEMPORARY_DAYCARE',FALSE,1.0,1.0,0.54,40,FALSE,FALSE,'Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus','Tilapäinen varhaiskasvatus', null),

    -- TEMPORARY_DAYCARE_PART_DAY
    ('7dedd9a8-a04c-11ec-a72b-3779d97aac97','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','tilapäinen osa-aikainen varhaiskasvatus','TEMPORARY_DAYCARE_PART_DAY',TRUE,1.0,1.0,1.0,20,TRUE,FALSE,'Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus', null),
    ('c288a706-9ef2-11ec-854e-8fec0360d917','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','tilapäinen osa-aikainen varhaiskasvatus','TEMPORARY_DAYCARE_PART_DAY',FALSE,1.0,1.0,1.0,20,TRUE,FALSE,'Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus','Tilapäinen osa-aikainen varhaiskasvatus', null),

    -- PREPARATORY_DAYCARE
    ('de463d82-9f97-11ec-a232-a782189e7c84', 'Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus','PREPARATORY',TRUE,0,1.0,0.54,25,TRUE,FALSE,'Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus', null),
    ('c288a74c-9ef2-11ec-854f-935955a88b27','Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus','PREPARATORY',FALSE,0,1.0,0.54,25,TRUE,FALSE,'Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus','Valmistava esiopetus', null)

ON CONFLICT (id) DO
UPDATE SET
    name_fi = EXCLUDED.name_fi,
    name_sv = EXCLUDED.name_sv,
    name_en = EXCLUDED.name_en,
    valid_placement_type = EXCLUDED.valid_placement_type,
    default_option = EXCLUDED.default_option,
    fee_coefficient = EXCLUDED.fee_coefficient,
    voucher_value_coefficient = EXCLUDED.voucher_value_coefficient,
    occupancy_coefficient = EXCLUDED.occupancy_coefficient,
    daycare_hours_per_week = EXCLUDED.daycare_hours_per_week,
    part_day = EXCLUDED.part_day,
    part_week = EXCLUDED.part_week,
    fee_description_fi = EXCLUDED.fee_description_fi,
    fee_description_sv = EXCLUDED.fee_description_sv,
    voucher_value_description_fi = EXCLUDED.voucher_value_description_fi,
    voucher_value_description_sv = EXCLUDED.voucher_value_description_sv,
    display_order = EXCLUDED.display_order
WHERE
    service_need_option.name_fi <> EXCLUDED.name_fi OR
    service_need_option.name_sv <> EXCLUDED.name_sv OR
    service_need_option.name_en <> EXCLUDED.name_en OR
    service_need_option.valid_placement_type <> EXCLUDED.valid_placement_type OR
    service_need_option.default_option <> EXCLUDED.default_option OR
    service_need_option.fee_coefficient <> EXCLUDED.fee_coefficient OR
    service_need_option.voucher_value_coefficient <> EXCLUDED.voucher_value_coefficient OR
    service_need_option.occupancy_coefficient <> EXCLUDED.occupancy_coefficient OR
    service_need_option.daycare_hours_per_week <> EXCLUDED.daycare_hours_per_week OR
    service_need_option.part_day <> EXCLUDED.part_day OR
    service_need_option.part_week <> EXCLUDED.part_week OR
    service_need_option.fee_description_fi <> EXCLUDED.fee_description_fi OR
    service_need_option.fee_description_sv <> EXCLUDED.fee_description_sv OR
    service_need_option.voucher_value_description_fi <> EXCLUDED.voucher_value_description_fi OR
    service_need_option.voucher_value_description_sv <> EXCLUDED.voucher_value_description_sv OR
    service_need_option.display_order <> EXCLUDED.display_order;
