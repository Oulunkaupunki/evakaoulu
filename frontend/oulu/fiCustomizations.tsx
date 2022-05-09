{
  /*
SPDX-FileCopyrightText: 2021 City of Oulu

SPDX-License-Identifier: LGPL-2.1-or-later
*/
}

import React from 'react'

import ExternalLink from 'lib-components/atoms/ExternalLink'
import UnorderedList from 'lib-components/atoms/UnorderedList'
import { H1, H2, P } from 'lib-components/typography'
import { Gap } from 'lib-components/white-space'
import { Translations } from 'lib-customizations/citizen'
import { DeepPartial } from 'lib-customizations/types'

const customerContactText = function () {
  return (
    <>
      {' '}
      p. <a href="tel:+358855845300">08 558 45300 </a> TAI{' '}
      <a href="mailto:varhaiskasvatus@ouka.fi">varhaiskasvatus@ouka.fi</a>.
    </>
  )
}

const fi: DeepPartial<Translations> = {
  applications: {
    creation: {
      daycareInfo:
        'Varhaiskasvatushakemuksella haetaan paikkaa kunnalliseen päiväkotiin tai perhepäivähoitoon. Samalla hakemuksella voi hakea myös varhaiskasvatuksen palveluseteliä yksityiseen varhaiskasvatukseen valitsemalla Hakutoiveet-kohtaan se yksityinen yksikkö, johon palveluseteliä halutaan hakea.',
      preschoolLabel:
        'Ilmoittautuminen esiopetukseen ja / tai valmistavaan opetukseen',
      preschoolInfo:
        'Maksutonta esiopetusta järjestetään neljä (4) tuntia päivässä. Lukuvuosi noudattaa pääosin koulujen työ- ja loma-aikoja. Tämän lisäksi lapselle voidaan hakea esiopetukseen liittyvää varhaiskasvatusta, jota tarjotaan esiopetuspaikoissa aamulla ennen esiopetuksen alkua ja iltapäivisin esiopetuksen jälkeen.',
      preschoolDaycareInfo: '',
      clubLabel: 'Hakemus avoimeen varhaiskasvatukseen',
      clubInfo:
        'Hakemuksella avoimeen varhaiskasvatukseen haetaan kahden ja kolmen kerran kerhoihin sekä perhekerhoon.',
      applicationInfo: (
        <P>
          Hakemukseen voi tehdä muutoksia siihen saakka, kunnes palveluohjaus on
          ottanut sen käsittelyyn. Tämän jälkeen muutokset tai hakemuksen
          peruminen tehdään ottamalla yhteyttä varhaiskasvatuksen
          palveluohjaukseen
          {customerContactText()}
        </P>
      ),
      duplicateWarning:
        'Lapsella on jo samantyyppinen, keskeneräinen hakemus. Palaa Hakemukset-näkymään ja muokkaa olemassa olevaa hakemusta tai ota yhteyttä varhaiskasvatuksen palveluohjaukseen.',
      transferApplicationInfo: {
        DAYCARE:
          'Lapsella on jo paikka Oulun varhaiskasvatuksessa. Tällä hakemuksella voit hakea siirtoa toiseen varhaiskasvatusta tarjoavaan yksikköön Oulussa.'
      }
    },
    editor: {
      sentInfo: {
        title: 'Hakemus on lähetetty',
        text: 'Hakemukseen voi tehdä muutoksia siihen saakka, kunnes palveluohjaus on ottanut sen käsittelyyn.',
        ok: 'Selvä!'
      },
      unitPreference: {
        siblingBasis: {
          title: 'Haku sisarusperusteella',
          info: {
            DAYCARE: (
              <>
                <P>
                  Lapsella on sisarusperuste samaan varhaiskasvatuspaikkaan,
                  jossa hänen sisaruksensa on päätöksentekohetkellä. Tavoitteena
                  on sijoittaa sisarukset samaan varhaiskasvatuspaikkaan perheen
                  niin toivoessa. Jos haet paikkaa sisaruksille, jotka eivät
                  vielä ole varhaiskasvatuksessa, kirjoita tieto hakemuksen Muut
                  lisätiedot -kohtaan.
                </P>
                <P>
                  Täytä nämä tiedot vain, jos käytät sisarusperustetta, sekä
                  valitse alla olevissa hakutoiveissa ensisijaiseksi toiveeksi
                  sama varhaiskasvatusyksikkö, jossa lapsen sisarus on.
                </P>
              </>
            ),
            CLUB: (
              <>
                <P>
                  Tavoitteena on sijoittaa sisarukset samaan kerhoryhmään
                  perheen niin toivoessa.
                </P>
                <P>
                  Täytä nämä tiedot vain, jos käytät sisarusperustetta, sekä
                  valitse alla olevassa Hakutoiveet-kohdassa ensisijaiseksi
                  toiveeksi sama avoimen varhaiskasvatuksen yksikkö, jossa
                  lapsen sisarus on.
                </P>
              </>
            )
          }
        },
        units: {
          info: {
            DAYCARE: (
              <>
                <P>
                  Voit hakea 1-3 paikkaa toivomassasi järjestyksessä.
                  Hakutoiveet eivät takaa paikkaa toivotussa yksikössä, mutta
                  mahdollisuus toivotun paikan saamiseen kasvaa antamalla
                  useamman vaihtoehdon.
                </P>
                <P>
                  Näet eri varhaiskasvatusyksiköiden sijainnin valitsemalla
                  ‘Yksiköt kartalla’.
                </P>
                <P>
                  Palveluseteliä haetaan valitsemalla hakutoiveeksi se
                  yksityinen varhaiskasvatusyksikkö, johon halutaan hakea.
                  Palveluseteliyksikköön haettaessa myös yksikön esimies saa
                  tiedon hakemuksesta.
                </P>
              </>
            ),
            PRESCHOOL: (
              <>
                <P>
                  Voit hakea 1-3 paikka paikkaa toivomassasi järjestyksessä.
                  Hakutoiveet eivät takaa paikkaa toivotussa yksikössä, mutta
                  mahdollisuus toivotun paikan saamiseen kasvaa antamalla
                  useamman vaihtoehdon.
                </P>
                <P>
                  Näet eri yksiköiden sijainnin valitsemalla ‘Yksiköt kartalla’.
                </P>
                <P>
                  Palveluseteliä haetaan valitsemalla hakutoiveeksi se
                  palveluseteliyksikkö, johon halutaan hakea.
                  Palveluseteliyksikköön haettaessa myös yksikön esimies saa
                  tiedon hakemuksesta.
                </P>
              </>
            ),
            CLUB: (
              <>
                <P>
                  Voit hakea 1-3 paikkaa toivomassasi järjestyksessä.
                  Hakutoiveet eivät takaa paikkaa toivotussa kerhossa, mutta
                  mahdollisuus toivotun paikan saamiseen kasvaa antamalla
                  useamman vaihtoehdon.
                </P>
                <P>
                  Näet avoimen varhaiskasvatusyksiköiden sijainnin valitsemalla
                  ‘Yksiköt kartalla’.
                </P>
              </>
            )
          },
          serviceVoucherLink:
            'https://www.ouka.fi/oulu/palveluseteli/yksityisen-paivahoidon-palveluseteli'
        }
      },
      heading: {
        title: {
          CLUB: 'Hakemus avoimeen varhaiskasvatukseen'
        },
        info: {
          DAYCARE: (
            <>
              <P>
                Hakemus on jätettävä viimeistään neljä kuukautta ennen kuin
                tarvitsette paikan. Mikäli tarvitsette varhaiskasvatusta
                kiireellisesti työn tai opiskelujen vuoksi, käsittelyaika on
                kaksi viikkoa hakemuksen saapumisesta.
              </P>
              <P>
                Saatte kirjallisen päätöksen varhaiskasvatuspaikasta
                Suomi.fi-viestit -palveluun tai postitse, mikäli ette ole
                ottaneet Suomi.fi-palvelua käyttöön. Päätös on nähtävillä myös
                eVaka-palvelussa kohdassa Hakeminen - Päätökset.
                Suomi.fi-viestit palvelusta ja sen käyttöönotosta saatte
                lisätietoa{' '}
                <ExternalLink
                  text="https://www.suomi.fi/viestit"
                  href="https://www.suomi.fi/viestit"
                  newTab
                />
                .
              </P>
              <P fitted={true}>* Tähdellä merkityt tiedot ovat pakollisia</P>
            </>
          ),
          PRESCHOOL: (
            <>
              <P>
                Esiopetukseen osallistutaan vuosi ennen oppivelvollisuuden
                alkamista. Esiopetus on maksutonta. Lukuvuoden 2022-2023
                esiopetukseen ilmoittaudutaan 10.-23.1.2022. Esiopetus alkaa
                15.8.2022.
              </P>
              <P>
                Päätökset tulevat{' '}
                <ExternalLink
                  text="Suomi.fi-viestit"
                  href="https://www.suomi.fi/viestit"
                  newTab
                />{' '}
                -palveluun tai postitse, mikäli et ole ottanut Suomi.fi
                -palvelua käyttöön.
              </P>
              <P fitted={true}>* Tähdellä merkityt tiedot ovat pakollisia</P>
            </>
          ),
          CLUB: (
            <>
              <P>
                Avoimen varhaiskasvatuksen kerhopaikka myönnetään siihen saakka,
                kunnes paikka irtisanotaan tai lapsi siirtyy varhaiskasvatukseen
                tai esiopetukseen. Avoimen toiminnan valintajärjestys on
                seuraava: lapset, jotka luopuvat varhaiskasvatuspaikasta,
                sisarukset, uudet hakijat hakujärjestyksessä huomioiden tuen
                tarvitsijat. Päätös kerhopaikasta tulee Suomi.fi-palveluun tai
                postitse, mikäli ette ole ottanut palvelua käyttöön. Päätös on
                nähtävillä myös eVaka-palvelussa kohdassa Hakeminen - Päätökset.
              </P>
              <P>
                Avoin varhaiskasvatustoiminta on maksutonta, eikä siihen
                osallistuminen vaikuta Kelan maksamaan kotihoidontukeen.
              </P>
              <P>
                Lisätietoja avoimesta varhaiskasvatuksesta Oulun kaupungin
                verkkosivuilta:{' '}
                <ExternalLink
                  text="Avoin varhaiskasvatus - kerhot ja leikkikoulut"
                  href="https://www.ouka.fi/oulu/paivahoito-ja-esiopetus/avoin-varhaiskasvatus"
                  newTab
                />
              </P>
              <P fitted={true}>* Tähdellä merkityt tiedot ovat pakollisia</P>
            </>
          )
        }
      },
      serviceNeed: {
        startDate: {
          header: {
            CLUB: 'Avoimen varhaiskasvatuksen alkaminen'
          },
          info: {
            PRESCHOOL: [
              'Esiopetus alkaa 15.8.2022. Jos tarvitsette esiopetukseen liittyvää varhaiskasvatusta, voitte hakea sitä kohdassa Esiopetukseen liittyvä varhaiskasvatus. Uutta varhaiskasvatushakemusta ei tarvitse tehdä, mikäli lapsella on jo paikka Oulun varhaiskasvatuksessa ja lapsi jatkaa esiopetuksessa samassa päiväkodissa.'
            ],
            CLUB: [
              'Avoimen varhaiskasvatuksen kerhot noudattavat pääsääntöisesti esiopetuksen työ- ja loma-aikoja. Lapsi voi osallistua yhteen kaksi tai kolme kertaa viikossa kokoontuvaan kerhoon ja lisäksi perhekerhoon.'
            ]
          },
          instructions: (
            <>
              Toivottua aloituspäivää on mahdollista muuttaa myöhemmäksi siihen
              saakka, kunnes palveluohjaus on ottanut sen käsittelyyn. Tämän
              jälkeen toivotun aloituspäivän muutokset tehdään ottamalla
              yhteyttä varhaiskasvatuksen palveluohjaukseen
              {customerContactText()}
            </>
          )
        },
        clubDetails: {
          wasOnDaycare:
            'Lapsella on varhaiskasvatuspaikka, josta luopuu kerhopaikan saadessaan',
          wasOnDaycareInfo:
            'Jos lapsi on ollut kunnallisessa päiväkodissa tai perhepäivähoidossa ja hän luopuu paikastaan kerhon alkaessa, hänellä on suurempi mahdollisuus saada kerhopaikka.',
          wasOnClubCareInfo:
            'Jos lapsi on ollut kerhossa jo edellisen toimintakauden aikana, hänellä on suurempi mahdollisuus saada paikka kerhosta myös tulevana toimintakautena.'
        },
        urgent: {
          attachmentsMessage: {
            text: (
              <P fitted={true}>
                Mikäli varhaiskasvatuspaikan tarve johtuu äkillisestä
                työllistymisestä tai opiskelusta, tulee paikkaa hakea
                viimeistään kaksi viikkoa ennen kuin tarve alkaa. Hakemuksen
                liitteenä tulee olla selvitys työ- tai opiskelupaikasta
                molemmilta samassa taloudessa asuvilta huoltajilta. Kahden
                viikon käsittelyaika alkaa siitä, kun olemme vastaanottaneet
                hakemuksen tarvittavine liitteineen.
              </P>
            ),
            subtitle:
              'Lisää tähän työ- tai opiskelutodistus molemmilta vanhemmilta.'
          }
        },
        shiftCare: {
          instructions:
            'Ilta- ja vuorohoidolla tarkoitetaan pääasiassa klo 6.00-18.00 ulkopuolella ja viikonloppuisin sekä ympärivuorokautisesti tapahtuvaa varhaiskasvatusta. Mikäli tarvitset ilta- tai vuorohoitoa, täsmennä tarvetta hakemuksen Muut lisätiedot -kohdassa.',
          message: {
            text: 'Ilta- ja vuorohoito on tarkoitettu lapsille, joiden molemmat vanhemmat ovat vuorotyössä tai opiskelevat pääsääntöisesti iltaisin ja/tai viikonloppuisin. Hakemuksen liitteenä tulee olla selvitys vuorotyöstä tai iltaisin ja/tai viikonloppuisin tapahtuvasta opiskelusta molemmilta samassa taloudessa asuvilta huoltajilta.'
          },
          attachmentsMessage: {
            text: 'Ilta- ja vuorohoito on tarkoitettu lapsille, joiden molemmat vanhemmat ovat vuorotyössä tai opiskelevat pääsääntöisesti iltaisin ja/tai viikonloppuisin. Hakemuksen liitteenä tulee olla selvitys vuorotyöstä tai iltaisin ja/tai viikonloppuisin tapahtuvasta opiskelusta molemmilta samassa taloudessa asuvilta huoltajilta.',
            subtitle:
              'Lisää tähän selvitys vuorotyöstä tai iltaisin ja/tai viikonloppuisin tapahtuvasta opiskelusta molempien huoltajien osalta.'
          }
        },
        assistanceNeedInstructions: {
          DAYCARE:
            'Tukitoimet toteutuvat lapsen arjessa osana varhaiskasvatuksen muuta toimintaa. Varhaiskasvatuksen erityisopettaja ottaa hakijaan yhteyttä, jotta lapsen tuen tarve voidaan ottaa huomioon varhaiskasvatuspaikkaa myönnettäessä.',
          CLUB: 'Tukitoimet toteutuvat lapsen arjessa osana varhaiskasvatuksen muuta toimintaa. Varhaiskasvatuksen erityisopettaja ottaa hakijaan yhteyttä, jotta lapsen tuen tarve voidaan ottaa huomioon varhaiskasvatuspaikkaa myönnettäessä.',
          PRESCHOOL:
            'Valitse hakemuksesta tämä kohta, jos lapsi tarvitsee kehitykselleen ja/tai oppimiselleen tukea esiopetusvuonna. Tukea toteutetaan lapsen arjessa osana esiopetuksen ja varhaiskasvatuksen muuta toimintaa. Varhaiskasvatuksen erityisopettaja ottaa hakijaan yhteyttä, jotta lapsen tuen tarve voidaan ottaa huomioon esiopetuspaikkaa osoitettaessa.'
        },
        partTime: {
          true: 'Osapäiväinen',
          false: 'Kokoaikainen'
        },
        dailyTime: {
          label: {
            DAYCARE: 'Palveluntarpeen vaihtoehdot'
          },
          connectedDaycareInfo: (
            <>
              <P>
                Voit hakea lapselle tarvittaessa esiopetukseen liittyvää
                maksullista varhaiskasvatusta. Jos haluat aloittaa
                varhaiskasvatuksen myöhemmin kuin esiopetus alkaa, kirjoita
                haluttu aloituspäivämäärä hakemuksen “Muut lisätiedot” -kohtaan.
                Palveluseteliä yksityiseen päiväkotiin haetaan valitsemalla
                hakutoiveeksi se palveluseteliyksikkö, johon halutaan hakea.
              </P>
              <P>
                Saat varhaiskasvatuspaikasta kirjallisen päätöksen{' '}
                <a
                  href="https://www.suomi.fi/viestit"
                  target="_blank"
                  rel="noreferrer"
                >
                  Suomi.fi-viestit
                </a>{' '}
                -palveluun tai postitse, mikäli et ole ottanut Suomi.fi-viestit
                -palvelua käyttöön. Päätös on nähtävillä myös eVaka-palvelussa
                kohdassa Hakeminen - Päätökset.
              </P>
            </>
          ),
          instructions: {
            PRESCHOOL:
              'Esiopetusta tarjotaan päiväkodeissa ja kouluissa neljä tuntia päivässä. Ilmoita lapsen tarvitsema varhaiskasvatusaika siten, että se sisältää myös esiopetusajan (esim. 7.00–17.00). Aika tarkennetaan varhaiskasvatuksen alkaessa. Päivittäisen varhaiskasvatusajan vaihdellessa päivittäin tai viikoittain (esim. vuorohoidossa), ilmoita tarve tarkemmin hakemuksen Muut lisätiedot -kohdassa.'
          },
          usualArrivalAndDeparture: {
            DAYCARE: 'Päivittäinen varhaiskasvatusaika '
          }
        },
        preparatory: 'Lapsi tarvitsee tukea suomen kielen oppimisessa.',
        preparatoryInfo:
          'Jokaiselle lapselle, jonka äidinkieli ei ole suomi, ruotsi tai saame, tehdään Pienten kielireppu -kielenkartoitus ja sen perusteella suomi toisena kielenä (s2) -opetussuunnitelma tai päätös perusopetukseen valmistavasta opetuksesta. S2-opetus sisällytetään päivittäiseen toimintaan lapsen tarpeiden mukaisesti. Valmistavaa opetusta annetaan kuusivuotiaille ja se koostuu päivittäin neljästä esiopetustunnista ja yhdestä suomen kielen tunnista. Valmistava opetus on maksutonta.'
      },
      contactInfo: {
        familyInfo: undefined,
        info: (
          <P data-qa="contact-info-text">
            Henkilötiedot on haettu väestötiedoista, eikä niitä voi muuttaa
            tällä hakemuksella. Jos henkilötiedoissa on virheitä, päivitäthän
            tiedot{' '}
            <ExternalLink
              text="Digi- ja Väestötietoviraston sivuilla"
              href="https://dvv.fi/henkiloasiakkaat"
              newTab
            />
            . Mikäli osoitteenne on muuttumassa, voit lisätä tulevan osoitteen
            erilliseen kohtaan hakemuksella; lisää tuleva osoite sekä lapselle
            että huoltajalle. Virallisena osoitetietoa pidetään vasta, kun se on
            päivittynyt väestötietojärjestelmään. Päätökset esiopetus- ja
            varhaiskasvatuspaikoista toimitetaan automaattisesti myös eri
            osoitteessa asuvalle väestötiedoista löytyvälle huoltajalle.
          </P>
        ),
        secondGuardianInfoPreschoolSeparated:
          'Toisen huoltajan tiedot haetaan automaattisesti väestötietojärjestelmästä. Tietojemme mukaan lapsen toinen huoltaja asuu eri osoitteessa. Esiopetukseen ilmoittautumisesta tulee sopia yhdessä toisen huoltajan kanssa.',
        secondGuardianAgreementStatus: {
          label:
            'Oletteko sopineet hakemuksen tekemisestä yhdessä toisen huoltajan kanssa?*',
          AGREED: 'Olemme yhdessä sopineet hakemuksen tekemisestä.',
          NOT_AGREED: 'Emme ole voineet sopia hakemuksen tekemisestä yhdessä.',
          RIGHT_TO_GET_NOTIFIED:
            'Toisella huoltajalla on vain tiedonsaantioikeus.'
        },
        futureAddressInfo:
          'Oulun varhaiskasvatuksessa virallisena osoitteena pidetään väestötiedoista saatavaa osoitetta. Osoite väestötiedoissa muuttuu hakijan tehdessä muuttoilmoituksen postiin tai Digi- ja väestötietovirastoon.'
      },
      fee: {
        info: {
          DAYCARE: (
            <P>
              Kunnallisen varhaiskasvatuksen asiakasmaksut ja palvelusetelin
              omavastuuosuus määräytyvät prosenttiosuutena perheen
              bruttotuloista. Maksut vaihtelevat perheen koon ja tulojen sekä
              varhaiskasvatusajan mukaan. Mikäli varhaiskasvatuspaikan hinta
              yksityisellä on enemmän kuin palvelusetelin arvo, erotuksen maksaa
              perhe. Perhe toimittaa tuloselvityksen bruttotuloistaan
              tuloselvityslomakkeella mahdollisimman pian siitä, kun lapsi on
              aloittanut varhaiskasvatuksessa.
            </P>
          ),
          PRESCHOOL: (
            <P>
              Esiopetus on maksutonta, mutta siihen liittyvä varhaiskasvatus on
              maksullista. Jos lapsi osallistuu esiopetukseen liittyvään
              varhaiskasvatukseen, perhe toimittaa tuloselvityksen
              bruttotuloistaan tulonselvityslomakkeella mahdollisimman pian
              siitä, kun lapsi on aloittanut varhaiskasvatuksessa.
            </P>
          )
        },
        links: (
          <>
            <P>
              Tuloselvityslomake löytyy eVakassa Käyttäjä-valikosta kohdasta
              Tulotiedot.
            </P>
            <P>
              Lisätietoa asiakasmaksuista löydät Oulun kaupungin nettisivuilta:{' '}
              <ExternalLink
                href="https://www.ouka.fi/oulu/paivahoito-ja-esiopetus/paivahoitomaksut"
                text="Varhaiskasvatuksen asiakasmaksut"
                newTab
              />
            </P>
          </>
        )
      },
      additionalDetails: {
        dietInfo: <>Ilmoita tähän lapsesi erityisruokavalio.</>
      }
    }
  },
  applicationsList: {
    title: 'Hakeminen varhaiskasvatukseen ja ilmoittautuminen esiopetukseen',
    summary: (
      <>
        <P width="800px">
          Lapsen huoltaja voi tehdä lapselle hakemuksen varhaiskasvatukseen ja
          avoimen varhaiskasvatuksen kerhoihin tai ilmoittaa lapsen
          esiopetukseen. Samalla hakemuksella voi hakea myös varhaiskasvatuksen
          palveluseteliä, hakemalla varhaiskasvatuspaikkaa yksityisestä
          päiväkodista. Huoltajan ja lasten tiedot haetaan tähän näkymään
          automaattisesti Väestötietojärjestelmästä.
        </P>
        <P width="800px">
          Jos lapsella on jo paikka Oulun varhaiskasvatuksessa ja halutaan hakea
          siirtoa toiseen yksikköön, tehdään lapselle uusi hakemus.
        </P>
      </>
    )
  },
  footer: {
    cityLabel: '© Oulun kaupunki',
    privacyPolicyLink: (
      <ExternalLink
        href="https://www.ouka.fi/oulu/verkkoasiointi/tietosuoja-ja-rekisteriselosteet-kasvatus-ja-koulutus"
        text="Tietosuojaselosteet"
        newTab={true}
        data-qa="footer-policy-link"
      ></ExternalLink>
    ),
    sendFeedbackLink: (
      <ExternalLink
        href="https://e-kartta.ouka.fi/efeedback"
        text="Lähetä palautetta"
        newTab={true}
        data-qa="footer-policy-link"
      ></ExternalLink>
    )
  },
  loginPage: {
    title: 'Oulun kaupungin varhaiskasvatus'
  },
  map: {
    mainInfo: `Tässä näkymässä voit hakea kartalta Oulun varhaiskasvatus-, esiopetus- ja avoimen varhaiskasvatuksen yksiköitä. Tietoa yksityisistä päiväkodeista löydät Oulun kaupungin nettisivuilta.`,
    privateUnitInfo: <></>,
    serviceVoucherLink:
      'https://www.ouka.fi/oulu/palveluseteli/yksityisen-paivahoidon-palveluseteli',
    searchPlaceholder: 'Esim. Ainolan päiväkoti',
    careTypes: {
      CLUB: 'Avoin varhaiskasvatus',
      DAYCARE: 'Varhaiskasvatus',
      PRESCHOOL: 'Esiopetus'
    }
  },
  decisions: {
    summary: (
      <P width="800px">
        Tälle sivulle saapuvat lapsen varhaiskasvatus- ja kerhohakemuksiin
        liittyvät päätökset ja ilmoitukset.
      </P>
    )
  },

  income: {
    description: (
      <>
        <p data-qa="income-description-p1">
          Tällä sivulla voit lähettää selvitykset varhaiskasvatusmaksuun
          vaikuttavista tuloistasi. Voit myös tarkastella palauttamiasi
          tuloselvityksiä ja muokata tai poistaa niitä kunnes viranomainen on
          käsitellyt tiedot. Lomakkeen käsittelyn jälkeen voit päivittää
          tulotietojasi toimittamalla uuden lomakkeen.
        </p>
        <p data-qa="income-description-p2">
          <strong>
            Molempien samassa taloudessa asuvien aikuisten tulee toimittaa omat
            erilliset tuloselvitykset.
          </strong>
        </p>
        <p data-qa="income-description-p3">
          Kunnallisen varhaiskasvatuksen asiakasmaksut määräytyvät
          prosenttiosuutena perheen bruttotuloista. Maksut vaihtelevat perheen
          koon ja tulojen sekä varhaiskasvatusajan mukaan.
        </p>
        <p data-qa="income-description-p4">
          <a href="https://www.ouka.fi/oulu/paivahoito-ja-esiopetus/paivahoitomaksut">
            Lisätietoja asiakasmaksuista
          </a>
        </p>
      </>
    ),
    incomeType: {
      description: (
        <>
          Jos olet yrittäjä, mutta sinulla on myös muita tuloja, valitse sekä{' '}
          <strong>Yrittäjän tulotiedot</strong>, että{' '}
          <strong>Asiakasmaksun määritteleminen bruttotulojen mukaan</strong>.
        </>
      ),
      startDate: 'Voimassa alkaen',
      endDate: 'Voimassaolo päättyy',
      title: 'Asiakasmaksun perusteet',
      agreeToHighestFee: 'Suostun korkeimpaan varhaiskasvatusmaksuun',
      highestFeeInfo:
        'Palveluntarpeen mukainen korkein maksu on voimassa toistaiseksi siihen saakka, kunnes toisin ilmoitan tai kunnes lapseni varhaiskasvatus päättyy. (Tulotietoja ei tarvitse toimittaa)',
      grossIncome: 'Maksun määritteleminen bruttotulojen mukaan',
      entrepreneurIncome: 'Yrittäjän tulotiedot'
    },
    grossIncome: {
      title: 'Bruttotulotietojen täyttäminen',
      description: (
        <>
          <P></P>
        </>
      ),
      incomeSource: 'Tulotietojen toimitus',
      provideAttachments: 'Toimitan tulotietoni liitteenä',
      estimate: 'Arvio bruttotuloistani',
      estimatedMonthlyIncome: 'Keskimääräiset tulot sisältäen lomarahat, €/kk',
      otherIncome: 'Muut tulot',
      otherIncomeDescription:
        'Jos sinulla on muita tuloja, on niistä toimitettavana tositteet liitteinä. Listan tarvittavista liitteistä löydät lomakkeen alaosasta kohdasta: Tuloihin ja varhaiskasvatusmaksuihin liittyvät liitteet.',
      choosePlaceholder: 'Valitse',
      otherIncomeTypes: {
        PENSION: 'Eläke',
        ADULT_EDUCATION_ALLOWANCE: 'Aikuiskoulutustuki',
        SICKNESS_ALLOWANCE: 'Sairauspäiväraha',
        PARENTAL_ALLOWANCE: 'Äitiys- ja vanhempainraha',
        HOME_CARE_ALLOWANCE: 'Lasten kotihoidontuki',
        FLEXIBLE_AND_PARTIAL_HOME_CARE_ALLOWANCE:
          'Joustava tai osittainen hoitoraha',
        ALIMONY: 'Elatusapu tai -tuki',
        INTEREST_AND_INVESTMENT_INCOME: 'Korko- ja osinkotulot',
        RENTAL_INCOME: 'Vuokratulot',
        UNEMPLOYMENT_ALLOWANCE: 'Työttömyyspäiväraha',
        LABOUR_MARKET_SUBSIDY: 'Työmarkkinatuki',
        ADJUSTED_DAILY_ALLOWANCE: 'Soviteltu päiväraha',
        JOB_ALTERNATION_COMPENSATION: 'Vuorotteluvapaakorvaus',
        REWARD_OR_BONUS: 'Palkkio tai bonus',
        RELATIVE_CARE_SUPPORT: 'Omaishoidontuki',
        BASIC_INCOME: 'Perustulo',
        FOREST_INCOME: 'Metsätulo',
        FAMILY_CARE_COMPENSATION: 'Perhehoidon palkkiot',
        REHABILITATION: 'Kuntoutustuki tai kuntoutusraha',
        EDUCATION_ALLOWANCE: 'Koulutuspäiväraha',
        GRANT: 'Apuraha',
        APPRENTICESHIP_SALARY: 'Palkkatulo oppisopimuskoulutuksesta',
        ACCIDENT_INSURANCE_COMPENSATION: 'Korvaus tapaturmavakuutuksesta',
        OTHER_INCOME: 'Muut tulot'
      },
      otherIncomeInfoLabel: 'Arviot muista tuloista',
      otherIncomeInfoDescription:
        'Kirjoita tähän arviot muiden tulojen määristä €/kk, esim. "Vuokratulot 150, lasten kotihoidontuki 300"'
    },
    moreInfo: {
      title: 'Muita maksuun liittyviä tietoja',
      studentLabel: 'Oletko opiskelija?',
      student: 'Olen opiskelija.',
      studentInfo:
        'Opiskelijat toimittavat oppilaitoksesta opiskelutodistuksen ja päätöksen opintoetuudesta.',
      deductions: 'Vähennykset',
      alimony:
        'Maksan elatusmaksuja. Toimitan kopion maksutositteesta liitteenä.',
      otherInfoLabel: 'Lisätietoja tulotietoihin liittyen'
    },
    attachments: {
      title: 'Tuloihin ja varhaiskasvatusmaksuihin liittyvät liitteet',
      description:
        'Toimita tässä tuloihin tai varhaiskasvatusmaksuihin liittyvät liitteet. Liitteitä ei tarvita, jos perheenne on suostunut korkeimpaan maksuun.'
    },
    selfEmployed: {
      info: '',
      attachments:
        'Toimitan liitteinä yrityksen viimeisimmän tulos- ja taselaskelman tai veropäätöksen.',
      estimatedIncome: 'Olen uusi yrittäjä. Täytän arvion keskimääräisistä kuukausituloistani. Toimitan tuloslaskelman ja taseen mahdollisimman pian.',
      estimatedMonthlyIncome: 'Keskimääräiset tulot €/kk',
      timeRange: 'Aikavälillä'
    },
    formDescription: (
      <>
        <P data-qa="income-formDescription-p1">
          Tuloselvitys liitteineen palautetaan mahdollisimman pian
          varhaiskasvatuksen aloittamisesta. Jos tulotietoja ei toimiteta tai ne
          ovat puutteelliset, maksu määräytyy korkeimman mukaan.
        </P>
        <P>
          Asiakasmaksu peritään päätöksen mukaisesta varhaiskasvatuksen
          alkamispäivästä lähtien.
        </P>
        <P>
          Asiakkaan on viipymättä ilmoitettava tulojen ja perhekoon muutoksista
          varhaiskasvatuksen asiakasmaksutiimiin. Viranomainen on tarvittaessa
          oikeutettu oikaisemaan varhaiskasvatusmaksuja myös takautuvasti.
        </P>
        <P>
          <strong>Huomioitavaa:</strong>
        </P>
        <Gap size="xs" />
        <UnorderedList data-qa="income-formDescription-ul">
          <li>
            Jos tulosi ylittävät perhekoon mukaisen tulorajan, hyväksy korkein
            varhaiskasvatusmaksu. Tällöin sinun ei tarvitse selvittää tulojasi
            lainkaan.
          </li>
          <li>
            Jos perheeseesi kuuluu toinen aikuinen, myös hänen on toimitettava
            tuloselvitys tunnistautumalla eVakaan omilla henkilötiedoillaan ja
            täyttämällä tämä lomake.
          </li>
          <li>
            Katso voimassaolevat tulorajat{' '}
            <a
              target="_blank"
              rel="noreferrer"
              href="https://www.ouka.fi/oulu/paivahoito-ja-esiopetus/paivahoitomaksut"
            >
              tästä
            </a>
            .
          </li>
        </UnorderedList>
        <P>* Tähdellä merkityt tiedot ovat pakollisia</P>
      </>
    )
  },
  accessibilityStatement: (
    <>
      <H1>Saavutettavuusseloste</H1>
      <P>
        Tämä saavutettavuusseloste koskee Oulun kaupungin varhaiskasvatuksen
        eVaka-verkkopalvelua osoitteessa{' '}
        <a href="https://varhaiskasvatus.ouka.fi">varhaiskasvatus.ouka.fi</a>.
        Oulun kaupunki pyrkii takaamaan verkkopalvelun saavutettavuuden,
        parantamaan käyttäjäkokemusta jatkuvasti ja soveltamaan asianmukaisia
        saavutettavuusstandardeja.
      </P>
      <P>
        Palvelun saavutettavuuden on arvioinut palvelun kehitystiimi, ja seloste
        on laadittu 12.4.2022.
      </P>
      <H2>Palvelun vaatimustenmukaisuus</H2>
      <P>
        Verkkopalvelu täyttää lain asettamat kriittiset
        saavutettavuusvaatimukset WCAG v2.1 -tason AA mukaisesti. Palvelu ei ole
        vielä kaikilta osin vaatimusten mukainen.
      </P>
      <H2>Toimet saavutettavuuden tukemiseksi</H2>
      <P>
        Verkkopalvelun saavutettavuus varmistetaan muun muassa seuraavilla
        toimenpiteillä:
      </P>
      <ul>
        <li>
          Saavutettavuus huomioidaan alusta lähtien suunnitteluvaiheessa, mm.
          valitsemalla palvelun värit ja kirjaisinten koot saavutettavasti.
        </li>
        <li>
          Palvelun elementit on määritelty semantiikaltaan johdonmukaisesti.
        </li>
        <li>Palvelua testataan jatkuvasti ruudunlukijalla.</li>
        <li>
          Erilaiset käyttäjät testaavat palvelua ja antavat saavutettavuudesta
          palautetta.
        </li>
        <li>
          Sivuston saavutettavuudesta huolehditaan jatkuvalla valvonnalla
          tekniikan tai sisällön muuttuessa.
        </li>
      </ul>
      <P>
        Tätä selostetta päivitetään sivuston muutosten ja saavutettavuuden
        tarkistusten yhteydessä.
      </P>
      <H2>Tunnetut saavutettavuusongelmat</H2>
      <P>
        Käyttäjät saattavat edelleen kohdata sivustolla joitakin ongelmia.
        Seuraavassa on kuvaus tunnetuista saavutettavuusongelmista. Jos huomaat
        sivustolla ongelman, joka ei ole luettelossa, otathan meihin yhteyttä.
      </P>
      <ul>
        <li>
          Viestit-sivulla liikkuminen näppäimistöllä tai ruudunlukijalla vaatii
          vielä korjauksia siirtymien ja kohdistettavien elementtien osalta.
        </li>
        <li>
          Palvelun yksikkökartassa ei pysty liikkumaan
          näppäimistöllä/ruudunlukijalla, mutta yksikköjä voi selata samassa
          näkymässä olevalta listalta. Palvelussa käytetty kartta on kolmannen
          osapuolen tuottama.
        </li>
      </ul>
      <H2>Kolmannet osapuolet</H2>
      <P>
        Verkkopalvelussa käytetään seuraavia kolmannen osapuolen palveluita,
        joiden saavutettavuudesta emme voi vastata.
      </P>
      <ul>
        <li>Keycloak käyttäjän tunnistautumispalvelu</li>
        <li>Suomi.fi-tunnistautuminen</li>
        <li>Leaflet-karttapalvelu</li>
      </ul>
      <H2>Vaihtoehtoiset asiointitavat</H2>
      <P>
        <ExternalLink
          href="https://www.ouka.fi/oulu/asiointi-ja-neuvonta"
          text="Oulun kaupungin asiointipisteistä"
        />{' '}
        saa apua sähköiseen asiointiin. Asiointipisteiden palveluneuvojat
        auttavat käyttäjiä, joille digipalvelut eivät ole saavutettavissa.
      </P>
      <H2>Anna palautetta</H2>
      <P>
        Jos huomaat saavutettavuuspuutteen verkkopalvelussamme, kerro siitä
        meille. Voit antaa palautetta{' '}
        <ExternalLink
          href="https://e-kartta.ouka.fi/efeedback"
          text="verkkolomakkeella"
        />{' '}
        tai sähköpostitse{' '}
        <a href="mailto:varhaiskasvatus@ouka.fi">varhaiskasvatus@ouka.fi</a>.
      </P>
      <H2>Valvontaviranomainen</H2>
      <P>
        Jos huomaat sivustolla saavutettavuusongelmia, anna ensin palautetta
        meille sivuston ylläpitäjille. Vastauksessa voi mennä 14 päivää. Jos et
        ole tyytyväinen saamaasi vastaukseen, tai et saa vastausta lainkaan
        kahden viikon aikana, voit antaa palautteen Etelä-Suomen
        aluehallintovirastoon. Etelä-Suomen aluehallintoviraston sivulla
        kerrotaan tarkasti, miten valituksen voi tehdä, ja miten asia
        käsitellään.
      </P>

      <P>
        <strong>Valvontaviranomaisen yhteystiedot </strong>
        <br />
        Etelä-Suomen aluehallintovirasto <br />
        Saavutettavuuden valvonnan yksikkö
        <br />
        <ExternalLink
          href="www.saavutettavuusvaatimukset.fi"
          text="www.saavutettavuusvaatimukset.fi"
        />
        <br />
        <a href="mailto:saavutettavuus@avi.fi">saavutettavuus@avi.fi</a>
        <br />
        puhelinnumero vaihde 0295 016 000
        <br />
        Avoinna: ma-pe klo 8.00–16.15
      </P>
    </>
  )
}

export default fi
