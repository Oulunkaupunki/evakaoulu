{
  /*
SPDX-FileCopyrightText: 2021 City of Oulu

SPDX-License-Identifier: LGPL-2.1-or-later
*/
}

import React from 'react'

import {
  daycareAssistanceLevels,
  preschoolAssistanceLevels
} from 'lib-common/generated/api-types/assistance'
import { H3, P } from 'lib-components/typography'
import { EmployeeCustomizations } from 'lib-customizations/types'

import OuluLogo from './city-logo.svg'
import featureFlags from './featureFlags'

const customizations: EmployeeCustomizations = {
  appConfig: {},
  translations: {
    fi: {
      preferredFirstName: {
        popupLink: 'Kutsumanimi',
        title: 'Kutsumanimi',
        description:
          'Voit määritellä eVakassa käytössä olevan kutsumanimesi. Kutsumanimen tulee olla jokin etunimistäsi. Jos nimesi on vaihtunut ja sinulla on tarve päivittää eVakaan uusi nimesi, ole yhteydessä Oulun HelpDeskiin.',
        select: 'Valitse kutsumanimi',
        confirm: 'Vahvista'
      },
      invoices: {
        buttons: {
          individualSendAlertText: ''
        }
      },
      // override translations here
      footer: {
        cityLabel: 'Oulun kaupunki',
        linkLabel: 'Oulun varhaiskasvatus',
        linkHref: 'https://www.ouka.fi/oulu/paivahoito-ja-esiopetus'
      },
      childInformation: {
        assistanceNeed: {
          title: 'Lapsen tuki',
          fields: {
            dateRange: 'Tuen tarve ajalle',
            capacityFactor: 'Lapsen paikkaluku',
            capacityFactorInfo:
              'Täytetään, kun lapsen paikkaluku/tuen kerroin on muu kuin 1. Yksityisen palvelusetelin korotus määräytyy tuen kertoimen mukaan. Täytetään, kun päätös tehty.',
            bases: 'Perusteet'
          }
        },
        assistanceAction: {
          title: 'Tukitoimet',
          fields: {
            dateRange: 'Tukitoimien voimassaoloaika',
            actions: 'Tukitoimet',
            actionTypes: {
              OTHER: 'Muu tukitoimi'
            },
            otherActionPlaceholder:
              'Voit kirjoittaa tähän lisätietoa muista tukitoimista.'
          }
        },
        assistanceNeedDecision: {
          jurisdiction: '',
          jurisdictionText: '',
          pedagogicalMotivationInfo:
            'Kirjaa tähän lyhyt yleiskuvaus lapsen tarvitsemasta tuesta ja tuen muodoista, jossa tulee esiin perustelu. Kirjaa konkreettisesti ja yksilöllisesti 2-3 olennaista tuen muotoa. Esim. Lapsi saa yksilöllisesti suunniteltua opetusta ja ohjausta kokonaiskehityksen tukemiseen. Esim. Lapsi saa kuvatukea ja pilkottuna annettuja ohjeita kielen kehityksen tueksi.',
          structuralMotivationInfo:
            'Valitse lapsen tarvitsemat rakenteellisen tuen muodot. Perustele, miksi lapsi saa näitä tuen muotoja. Esim. Tuki huomioidaan lapsiryhmän koossa. Esim. Lapsi saa ryhmäkohtaisen avustajan tukea vuorovaikutustilanteisiin ja keskittymisen tukemiseen. Esim. Lapsi saa lapsikohtaisen avustajan tukea kokonaiskehityksen tukemiseen. Esim. Lapsi saa tukea alueellisessa pienryhmässä tai lapsi saa tukea erityisryhmässä.',
          careMotivationInfo:
            'Kirjaa tähän lapsen tarvitsemat hoidollisen tuen muodot, esim. apuvälineiden käyttö, perushoito ja pitkäaikaissairauksien hoito. Perustele, miksi lapsi saa hoidollisen tuen muotoja. Esim. Lapsi saa hoidollista tukea sairauteen liittyvissä hoitotoimenpiteissä. Esim. Lapsi saa tukea apuvälineen käytössä. Huom: lapsen vasuun/leopsiin/HOJKS:aan kirjataan tarkemmin apuvälineiden käytöstä.',
          servicesInfo:
            'Valitse tästä lapselle esitettävät tukipalvelut. Perustele, miksi lapsi saa näitä tukipalveluja. Esim. Lapsi saa varhaiskasvatuksen erityisopettajan osa-aikaista opetusta kielenkehityksen tukemiseksi. Esim. Varhaiskasvatuksen erityisopettaja antaa konsultaatiota tuen järjestämiseksi.',
          guardiansHeardInfo:
            'Kaikilla lapsen huoltajilla tulee olla mahdollisuus tulla kuulluksi. Huoltaja voi tarvittaessa valtuuttaa toisen huoltajan edustamaan itseään valtakirjalla. Kirjaa tähän millä keinoin huoltajaa tai muuta laillista edustajaa on kuultu (esim. palaveri, etäyhteys, huoltajan kirjallinen vastine) sekä päivämäärä. Jos huoltajaa ei ole kuultu, kirjaa tähän miten ja milloin hänet on kutsuttu kuultavaksi. Esim. Kirjallinen kutsu lapsen tukeen liittyvään kuulemistilaisuuteen on lähetetty huoltajille eVakassa pvm.',
          viewOfTheGuardiansInfo:
            'Kirjaa tähän huoltajien näkemys lapselle esitetystä tuesta. Esim. Huoltajat ovat samaa mieltä esitetystä tuesta. Kirjaa näkemys niin, kuin huoltajat sen ilmaisevat tai liitä huoltajan kirjallinen näkemys.',
          startDateIndefiniteInfo:
            'Tehostettu ja erityinen tuki ovat voimassa toistaiseksi.',
          startDateInfo:
            'Yleisenä tukena annettavat tukipalvelut ovat voimassa määräajan. Voimassa alkaen päivämäärä on se, kun viimeistäkin huoltajaa on kuultu.',
          appealInstructions: (
            <>
              <P>
                Tähän päätökseen tyytymätön voi tehdä kirjallisen
                oikaisuvaatimuksen.
              </P>
              <H3>Oikaisuvaatimusoikeus</H3>
              <P>
                Oikaisuvaatimuksen saa tehdä se, johon päätös on kohdistettu tai
                jonka oikeuteen, velvollisuuteen tai etuun päätös välittömästi
                vaikuttaa (asianosainen).
              </P>
              <H3>Oikaisuviranomainen</H3>
              <P>Oikaisu tehdään Pohjois-Suomen aluehallintovirastolle.</P>
              <P>
                Pohjois-Suomen aluehallintovirasto
                <br />
                Käyntiosoite: Linnankatu 3, 90100 Oulu
                <br />
                Postiosoite: PL 6, 13035 AVI
                <br />
                Sähköpostiosoite: kirjaamo.pohjois@avi.fi
                <br />
                Puhelinvaihde: 0295 017 500
              </P>
              <H3>Oikaisuvaatimusaika</H3>
              <P>
                Oikaisuvaatimus on tehtävä 30 päivän kuluessa päätöksen
                tiedoksisaannista.
              </P>
              <H3>Tiedoksisaanti</H3>
              <P>
                Asianosaisen katsotaan saaneen päätöksestä tiedon, jollei muuta
                näytetä, 7 päivän kuluttua kirjeen lähettämisestä, 3 päivän
                kuluttua sähköpostin lähettämisestä, saantitodistuksen
                osoittamana aikana tai erilliseen tiedoksisaantitodistukseen
                merkittynä aikana. Tiedoksisaantipäivää ei lueta määräaikaan.
                Jos määräajan viimeinen päivä on pyhäpäivä, itsenäisyyspäivä,
                vapunpäivä, joulu- tai juhannusaatto tai arkilauantai, saa
                tehtävän toimittaa ensimmäisenä arkipäivänä sen jälkeen.
              </P>
              <H3>Oikaisuvaatimus</H3>
              <P noMargin>Oikaisuvaatimuksessa on ilmoitettava</P>
              <ul>
                <li>
                  Oikaisuvaatimuksen tekijän nimi, kotikunta, postiosoite ja
                  puhelinnumero
                </li>
                <li>päätös, johon haetaan oikaisua</li>
                <li>
                  miltä osin päätökseen haetaan oikaisua ja mitä oikaisua siihen
                  vaaditaan tehtäväksi
                </li>
                <li>vaatimuksen perusteet</li>
              </ul>
              <P noMargin>Oikaisuvaatimukseen on liitettävä</P>
              <ul>
                <li>
                  päätös, johon haetaan oikaisua, alkuperäisenä tai
                  jäljennöksenä
                </li>
                <li>
                  todistus siitä, minä päivänä päätös on annettu tiedoksi, tai
                  muu selvitys oikaisuvaatimusajan alkamisen ajankohdasta
                </li>
                <li>
                  asiakirjat, joihin oikaisuvaatimuksen tekijä vetoaa
                  oikaisuvaatimuksensa tueksi, jollei niitä ole jo aikaisemmin
                  toimitettu viranomaiselle.
                </li>
              </ul>
              <P>
                Asiamiehen on liitettävä valituskirjelmään valtakirja, kuten
                oikeudenkäynnistä hallintoasioissa annetun lain (808/2019) 32
                §:ssä säädetään.
              </P>
              <H3>Oikaisuvaatimuksen toimittaminen</H3>
              <P>
                Oikaisuvaatimuskirjelmä on toimitettava oikaisuvaatimusajan
                kuluessa oikaisuvaatimusviranomaiselle. Oikaisuvaatimuskirjelmän
                tulee olla perillä oikaisuvaatimusajan viimeisenä päivänä ennen
                viraston aukiolon päättymistä. Oikaisuvaatimuksen lähettäminen
                postitse tai sähköisesti tapahtuu lähettäjän omalla vastuulla.
              </P>
            </>
          )
        },
        assistanceNeedPreschoolDecision: {
          pageTitle: 'Päätös erityisestä tuesta esiopetuksessa',
          lawReference: 'Perusopetuslaki 17 § ja 31 §',
          appealInstructions: (
            <>
              <P>
                Tähän päätökseen tyytymätön voi tehdä kirjallisen
                oikaisuvaatimuksen.
              </P>
              <H3>Oikaisuvaatimusoikeus</H3>
              <P>
                Oikaisuvaatimuksen saa tehdä se, johon päätös on kohdistettu tai
                jonka oikeuteen, velvollisuuteen tai etuun päätös välittömästi
                vaikuttaa (asianosainen).
              </P>
              <H3>Oikaisuviranomainen</H3>
              <P>Oikaisu tehdään Pohjois-Suomen aluehallintovirastolle.</P>
              <P>
                Pohjois-Suomen aluehallintovirasto
                <br />
                Käyntiosoite: Linnankatu 3, 90100 Oulu
                <br />
                Postiosoite: PL 6, 13035 AVI
                <br />
                Sähköpostiosoite: kirjaamo.pohjois@avi.fi
                <br />
                Puhelinvaihde: 0295 017 500
              </P>
              <H3>Oikaisuvaatimusaika</H3>
              <P>
                Oikaisuvaatimus on tehtävä 14 päivän kuluessa päätöksen
                tiedoksisaannista.
              </P>
              <H3>Tiedoksisaanti</H3>
              <P>
                Asianosaisen katsotaan saaneen päätöksestä tiedon, jollei muuta
                näytetä, 7 päivän kuluttua kirjeen lähettämisestä, 3 päivän
                kuluttua sähköpostin lähettämisestä, saantitodistuksen
                osoittamana aikana tai erilliseen tiedoksisaantitodistukseen
                merkittynä aikana. Tiedoksisaantipäivää ei lueta määräaikaan.
                Jos määräajan viimeinen päivä on pyhäpäivä, itsenäisyyspäivä,
                vapunpäivä, joulu- tai juhannusaatto tai arkilauantai, saa
                tehtävän toimittaa ensimmäisenä arkipäivänä sen jälkeen.
              </P>
              <H3>Oikaisuvaatimus</H3>
              <P noMargin>Oikaisuvaatimuksessa on ilmoitettava</P>
              <ul>
                <li>
                  Oikaisuvaatimuksen tekijän nimi, kotikunta, postiosoite ja
                  puhelinnumero
                </li>
                <li>päätös, johon haetaan oikaisua</li>
                <li>
                  miltä osin päätökseen haetaan oikaisua ja mitä oikaisua siihen
                  vaaditaan tehtäväksi
                </li>
                <li>vaatimuksen perusteet</li>
              </ul>
              <P noMargin>Oikaisuvaatimukseen on liitettävä</P>
              <ul>
                <li>
                  päätös, johon haetaan oikaisua, alkuperäisenä tai
                  jäljennöksenä
                </li>
                <li>
                  todistus siitä, minä päivänä päätös on annettu tiedoksi, tai
                  muu selvitys oikaisuvaatimusajan alkamisen ajankohdasta
                </li>
                <li>
                  asiakirjat, joihin oikaisuvaatimuksen tekijä vetoaa
                  oikaisuvaatimuksensa tueksi, jollei niitä ole jo aikaisemmin
                  toimitettu viranomaiselle.
                </li>
              </ul>
              <P>
                Asiamiehen on liitettävä valituskirjelmään valtakirja, kuten
                oikeudenkäynnistä hallintoasioissa annetun lain (808/2019) 32
                §:ssä säädetään.
              </P>
              <H3>Oikaisuvaatimuksen toimittaminen</H3>
              <P>
                Oikaisuvaatimuskirjelmä on toimitettava oikaisuvaatimusajan
                kuluessa oikaisuvaatimusviranomaiselle. Oikaisuvaatimuskirjelmän
                tulee olla perillä oikaisuvaatimusajan viimeisenä päivänä ennen
                viraston aukiolon päättymistä. Oikaisuvaatimuksen lähettäminen
                postitse tai sähköisesti tapahtuu lähettäjän omalla vastuulla.
              </P>
            </>
          )
        },
        assistanceNeedVoucherCoefficient: {
          sectionTitle: 'Palvelusetelikerroin',
          voucherCoefficient: 'Palvelusetelikerroin',
          create: 'Aseta uusi palvelusetelikerroin',
          form: {
            title: 'Aseta uusi palvelusetelikerroin',
            editTitle: 'Muokkaa palvelusetelikerrointa',
            titleInfo:
              'Valitse palvelusetelikertoimen voimassaolopäivämäärät korotetun palvelusetelipäätöksen mukaisesti.',
            coefficient: 'Palvelusetelikerroin (luku)',
            validityPeriod: 'Palvelusetelikerroin voimassa',
            errors: {
              previousOverlap:
                'Aiempi päällekkäinen palvelusetelikerroin katkaistaan automaattisesti.',
              upcomingOverlap:
                'Tuleva päällekkäinen palvelusetelikerroin siirretään alkamaan myöhemmin automaattisesti.',
              fullOverlap:
                'Edellinen päällekkäinen palvelusetelikerroin poistetaan automaattisesti.',
              coefficientRange: 'Kerroin tulee olla välillä 1-10'
            }
          },
          deleteModal: {
            title: 'Poistetaanko palvelusetelikerroin?',
            description:
              'Haluatko varmasti poistaa palvelusetelikertoimen? Asiakkaalle ei luoda uutta arvopäätöstä, vaikka kertoimen poistaisi, vaan sinun tulee tehdä uusi takautuva arvopäätös.',
            delete: 'Poista kerroin'
          }
        },
        dailyServiceTimes: {
          info: 'Tallenna tähän varhaiskasvatussopimuksella sovittu päivittäinen läsnäoloaika.',
          info2: ''
        }
      },
      unit: {
        placementProposals: {
          rejectReasons: {
            REASON_1:
              'Tilarajoite, sovittu varhaiskasvatuksen aluepäällikön kanssa',
            REASON_2:
              'Yksikön kokonaistilanne, sovittu varhaiskasvatuksen aluepäällikön kanssa'
          },
          infoTitle: '',
          infoText: ''
        }
      },
      login: {
        loginAD: 'Oulu AD'
      },
      placement: {
        type: {
          DAYCARE: 'Varhaiskasvatus',
          DAYCARE_PART_TIME: 'Osapäiväinen varhaiskasvatus',
          TEMPORARY_DAYCARE: 'Tilapäinen kokopäiväinen varhaiskasvatus',
          PRESCHOOL_DAYCARE: 'Esiopetusta täydentävä varhaiskasvatus',
          CLUB: 'Kerho'
        }
      },
      unitEditor: {
        placeholder: {
          phone: 'esim. +358 40 555 5555',
          email: 'etunimi.sukunimi@ouka.fi',
          url: 'esim. https://www.ouka.fi/oulu/ainolan-paivakoti/etusivu',
          streetAddress: 'Kadun nimi esim. Radiomastontie 12',
          decisionCustomization: {
            name: 'esim. Ainolan päiväkoti'
          }
        },
        field: {
          decisionCustomization: {
            handler: ['Palveluohjaus', 'Varhaiskasvatusyksikön johtaja']
          }
        }
      },
      welcomePage: {
        text: 'Olet kirjautunut sisään eVaka Oulu -palveluun. Käyttäjätunnuksellesi ei ole vielä annettu oikeuksia, jotka mahdollistavat palvelun käytön. Tarvittavat käyttöoikeudet saat omalta esimieheltäsi.'
      },
      absences: {
        title: 'Poissaolot',
        absenceTypes: {
          OTHER_ABSENCE: 'Poissaolo',
          SICKLEAVE: 'Sairaus',
          UNKNOWN_ABSENCE: 'Ilmoittamaton poissaolo',
          PLANNED_ABSENCE: 'Sopimuspoissaolo',
          TEMPORARY_RELOCATION: 'Lapsi varasijoitettuna muualla',
          PARENTLEAVE: 'Vanhempainvapaa',
          FORCE_MAJEURE: 'Hyvityspäivä',
          FREE_ABSENCE: 'Maksuton poissaolo',
          NO_ABSENCE: 'Ei poissaoloa'
        },
        absenceTypesShort: {
          OTHER_ABSENCE: 'Poissaolo',
          SICKLEAVE: 'Sairaus',
          UNKNOWN_ABSENCE: 'Ilmoittamaton',
          PLANNED_ABSENCE: 'Sopimus',
          TEMPORARY_RELOCATION: 'Varasijoitus',
          PARENTLEAVE: 'Vanhempainvapaa',
          FORCE_MAJEURE: 'Hyvitys',
          FREE_ABSENCE: 'Maksuton',
          NO_ABSENCE: 'Ei poissa'
        },
        absenceTypeInfo: {
          OTHER_ABSENCE:
            'Käytetään aina, kun huoltaja on ilmoittanut poissaolosta mukaan lukien säännölliset vapaat ja loma-ajat. Käytetään myös vuoroyksiköissä lasten lomamerkinnöissä tai muissa poissaoloissa, jotka ovat suunniteltujen läsnäolovarausten ulkopuolella.',
          SICKLEAVE:
            'Merkitään, kun lapsi on sairaana tai kuntoutus- / tutkimusjaksolla.',
          UNKNOWN_ABSENCE:
            'Käytetään silloin, kun huoltaja ei ole ilmoittanut poissaolosta. Koodi muutetaan vain, jos kyseessä on sairauspoissaolo.',
          PLANNED_ABSENCE:
            'Palveluntarvesopimuksen (10 tai 13 pv/kk) mukaiset etukäteen ilmoitetut poissaolot.',
          TEMPORARY_RELOCATION:
            'Lapselle on tehty varasijoitus toiseen yksikköön. Poissa omasta, läsnä muualla.',
          PARENTLEAVE:
            'Poissaolo merkitään sille lapselle, josta maksetaan vanhempainrahaa.',
          FORCE_MAJEURE:
            'Käytetään vain erikoistilanteissa hallinnon ohjeiden mukaan. Yksittäisiä päiviä, joista luvattu maksuhyvitys.',
          FREE_ABSENCE: 'Kesäajan maksuton poissaolo',
          NO_ABSENCE: 'Jos lapsi on paikalla, älä merkitse mitään.'
        },
        modal: {
          absenceSectionLabel: 'Poissaolon syy',
          placementSectionLabel: 'Toimintamuoto, jota poissaolo koskee',
          saveButton: 'Tallenna',
          cancelButton: 'Peruuta',
          absenceTypes: {
            OTHER_ABSENCE: 'Poissaolo',
            SICKLEAVE: 'Sairaus',
            UNKNOWN_ABSENCE: 'Ilmoittamaton poissaolo',
            PLANNED_ABSENCE: 'Sopimuspoissaolo',
            TEMPORARY_RELOCATION: 'Lapsi varasijoitettuna muualla',
            PARENTLEAVE: 'Vanhempainvapaa',
            FORCE_MAJEURE: 'Hyvityspäivä',
            FREE_ABSENCE: 'Maksuton poissaolo',
            NO_ABSENCE: 'Ei poissaoloa'
          },
          free: 'Maksuton',
          paid: 'Maksullinen',
          absenceSummaryTitle: 'Lapsen poissaolokooste'
        }
      },
      reports: {
        invoices: {
          title: 'Laskujen täsmäytys',
          description:
            'Laskujen täsmäytysraportti Monetra-järjestelmän vertailua varten',
          areaCode: 'Alue',
          amountOfInvoices: 'Laskuja',
          totalSumCents: 'Summa',
          amountWithoutSSN: 'Hetuttomia',
          amountWithoutAddress: 'Osoitteettomia',
          amountWithZeroPrice: 'Nollalaskuja'
        }
      }
    },
    sv: {}
  },
  vasuTranslations: {
    FI: {},
    SV: {}
  },
  cityLogo: {
    src: OuluLogo,
    alt: 'Oulu logo'
  },
  featureFlags,
  placementTypes: [
    'CLUB',
    'DAYCARE',
    'DAYCARE_PART_TIME',
    'TEMPORARY_DAYCARE',
    'TEMPORARY_DAYCARE_PART_DAY',
    'PRESCHOOL',
    'PRESCHOOL_DAYCARE',
    'PREPARATORY',
    'PREPARATORY_DAYCARE'
  ],
  absenceTypes: [
    'OTHER_ABSENCE',
    'UNKNOWN_ABSENCE',
    'PLANNED_ABSENCE',
    'SICKLEAVE',
    'PARENTLEAVE',
    'FORCE_MAJEURE'
  ],
  voucherValueDecisionTypes: [
    'NORMAL',
    'RELIEF_ACCEPTED',
    'RELIEF_PARTLY_ACCEPTED',
    'RELIEF_REJECTED'
  ],
  daycareAssistanceLevels: [...daycareAssistanceLevels],
  otherAssistanceMeasureTypes: [
    'TRANSPORT_BENEFIT',
    'ANOMALOUS_EDUCATION_START',
    'CHILD_DISCUSSION_OFFERED',
    'CHILD_DISCUSSION_HELD',
    'CHILD_DISCUSSION_COUNSELING'
  ],
  placementPlanRejectReasons: ['REASON_1', 'REASON_2', 'OTHER'],
  preschoolAssistanceLevels: [...preschoolAssistanceLevels],
  unitProviderTypes: [
    'MUNICIPAL',
    'PRIVATE',
    'PRIVATE_SERVICE_VOUCHER',
    'PURCHASED'
  ]
}

export default customizations
