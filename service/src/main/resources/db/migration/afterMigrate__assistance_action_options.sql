-- SPDX-FileCopyrightText: 2021 City of Oulu
--
-- SPDX-License-Identifier: LGPL-2.1-or-later

INSERT INTO assistance_action_option
    (value, name_fi, description_fi, display_order)
VALUES
    ('10', 'Avustamispalvelut: ryhmäkohtainen', 'Merkitään alueellisen tuen vastaavan päätöksen jälkeen.', 10),
    ('20', 'Avustamispalvelut: lapsikohtainen', 'Merkitään alueellisen tuen vastaavan päätöksen jälkeen.', 20),
    ('30', 'Tuki huomioidaan lapsiryhmän koossa', 'Merkitään alueellisen tuen vastaavan päätöksen jälkeen', 30),
    ('40', 'Varhaiskasvatuksen erityisopettajan konsultaatio', 'Merkitään, kun lapsikohtainen konsultaatio on säännöllistä.', 40),
    ('50', 'Osa-aikainen erityisopetus', 'Lapsi saa osa-aikaisesti varhaiskasvatuksen erityisopettajan antamaa opetusta.', 50),
    ('60', 'Kokoaikainen erityisopetus', 'Lapsi saa kokoaikaisesti varhaiskasvatuksen erityisopettajan antamaa opetusta.', 60),
    ('70', 'Ryhmän henkilöstömäärän lisäys', 'Esim. neljäs kasvattaja ryhmässä, resurssiveo ryhmässä.', 70),
    ('80', 'Integroitu varhaiskasvatusryhmä', 'Ryhmässä kokoaikainen varhaiskasvatuksen erityisopettaja.', 80),
    ('90', 'Alueellinen pienryhmä', 'Ryhmässä kokoaikainen varhaiskasvatuksen erityisopettaja.', 90),
    ('100', 'Pienryhmä', 'Ryhmässä kokoaikainen varhaiskasvatuksen erityisopettaja.', 100),
    ('110', 'Alueellinen erityisryhmä', 'Ryhmässä kokoaikainen varhaiskasvatuksen erityisopettaja.', 110),
    ('120', 'Erityisryhmä', 'Ryhmässä kokoaikainen varhaiskasvatuksen erityisopettaja.', 120),
    ('130', 'Apuvälineet', 'Merkitään hallintopäätöksen jälkeen.', 130),
    ('140', 'Tulkitsemispalvelut', 'Lapselle järjestetään kommunikaation tueksi erillisiä tulkitsemispalveluita. Viittomakieltä käyttävien lasten kommunikaation tukena voidaan tarvittaessa käyttää viittomakielen tulkkia tai viittomakielentaitoista avustajaa. Tulkitsemispalvelut on mahdollista järjestää avustamispalvelun yhteydessä.', 140),
    ('150', 'Kuljetusetu varhaiskasvatus', 'Merkitään päätöksen jälkeen. Lapselle on myönnetty taksikuljetus varhaiskasvatukseen.', 150),
    ('160', 'Kuljetusetu esiopetus', 'Merkitään päätöksen jälkeen. Lapselle on myönnetty taksikuljetus esiopetukseen.', 160),
    ('170', 'Erityishuolto-ohjelma', 'Kehitysvammalain nojalla myönnettyjä palveluja saaville lapsille laaditaan erityishuolto-ohjelma. Erityishuolto-ohjelma sisältää asiakkaalle kehitysvammalain nojalla myönnetyt palvelut.', 170)
ON CONFLICT (value) DO
UPDATE SET
    name_fi = EXCLUDED.name_fi,
    description_fi = EXCLUDED.description_fi,
    display_order = EXCLUDED.display_order
WHERE
    assistance_action_option.name_fi <> EXCLUDED.name_fi OR
    assistance_action_option.description_fi IS DISTINCT FROM EXCLUDED.description_fi OR
    assistance_action_option.display_order <> EXCLUDED.display_order;
