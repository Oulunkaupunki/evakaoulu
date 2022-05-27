-- SPDX-FileCopyrightText: 2021 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

INSERT INTO assistance_basis_option
    (value, name_fi, description_fi, display_order)
VALUES
    ('CHILD_ACCULTURATION_SUPPORT', 'Lapsen ja perheen kotoutumissuunnitelma', 'Lapsen ja perheen kotoutumisen tuki voidaan myöntää, kun perheen lapsi tulee ensimmäistä kertaa suomalaiseen päiväkotiin. Jos perheen muita lapsia on tällä hetkellä tai on ollut aiemmin suomalaisessa päiväkodissa, kotoutumisen tukea ei enää myönnetä. Pakolaistaustaisen perheen ollessa kyseessä aika on 6 kk, muiden osalta 3kk.', 100),
    ('COMMON_SUPPORT', 'Yleinen tuki', 'Ei hallintopäätöstä.', 150),
    ('INTENSIFIED_ASSISTANCE', 'Tehostettu tuki', 'Merkitään hallintopäätöksen jälkeen.', 200),
    ('SPECIAL_ASSISTANCE_DECISION', 'Erityinen tuki', 'Merkitään hallintopäätöksen jälkeen.', 300),
    ('EXTENDED_COMPULSORY_EDUCATION', 'Pidennetty oppivelvollisuus', 'Merkitään hallintopäätöksen jälkeen.', 310),
    ('DIFFERENT_EDUCTATION_START_DATE', 'Opetuksen poikkeava aloittamisajankohta', 'Merkitään päätöksen jälkeen.', 320),
    ('DEVELOPMENTAL_DISABILITY', 'Kehitysvamma', 'Lapsella on todettu kehitysvamma, tieto tarvitaan tilastointia varten.', 330),
    ('FINNISH_AS_SECOND_LANGUAGE', 'Suomi toisena kielenä opetus', 'Mikäli lapsi on alkeiskielitasolla ja saa suomi toisena kielenä opettajan antamaa opetusta', 340),
    ('PREPARATORY_EDUCATION_ASSISTANCE', 'Valmistava opetus ', 'Valmistavaa opetusta annetaan esiopetusikäisille maahanmuuttajataustaisille, joilla ei ole vielä tarvittavia kielellisiä valmiuksia esiopetuksessa opiskeluun (alkeiskielitaso). Merkitään palveluohjauksen päätöksen jälkeen', 350)
ON CONFLICT (value) DO
UPDATE SET
    name_fi = EXCLUDED.name_fi,
    description_fi = EXCLUDED.description_fi,
    display_order = EXCLUDED.display_order
WHERE
    assistance_basis_option.name_fi <> EXCLUDED.name_fi OR
    assistance_basis_option.description_fi IS DISTINCT FROM EXCLUDED.description_fi OR
    assistance_basis_option.display_order <> EXCLUDED.display_order;
