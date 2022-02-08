{
  /*
SPDX-FileCopyrightText: 2021 City of Oulu

SPDX-License-Identifier: LGPL-2.1-or-later
*/
}

import { P } from 'lib-components/typography'
import UnorderedList from 'lib-components/atoms/UnorderedList'
import { Gap } from 'lib-components/white-space'
import { Translations } from 'lib-customizations/citizen'
import { DeepPartial } from 'lib-customizations/types'
import React from 'react'
import ExternalLink from 'lib-components/atoms/ExternalLink'

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
        'Varhaiskasvatushakemuksella haetaan paikkaa kunnallisesta päiväkodista tai perhepäivähoidosta, ostopalvelupäiväkodista tai palvelusetelillä tuetusta päiväkodista.',
      preschoolLabel:
        'Ilmoittautuminen esiopetukseen ja / tai valmistavaan opetukseen',
      preschoolInfo:
        'Ilmoittautumisen yhteydessä voidaan hakea myös esiopetukseen liittyvään varhaiskasvatukseen. Maksutonta esiopetusta järjestetään neljä (4) tuntia päivässä. Lukuvuosi noudattaa pääosin koulujen loma- ja työaikoja. Tämän lisäksi lapselle voidaan hakea esiopetukseen liittyvää varhaiskasvatusta, jota tarjotaan esiopetuspaikoissa aamulla ennen esiopetuksen alkua ja iltapäivisin esiopetuksen jälkeen.',
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
      unitPreference: {
        siblingBasis: {
          info: {
            DAYCARE: (
              <>
                <P>
                  Lapsella on sisarusperuste samaan varhaiskasvatuspaikkaan, 
                  jossa hänen sisaruksensa on päätöksentekohetkellä. Tavoitteena 
                  on sijoittaa sisarukset samaan varhaiskasvatuspaikkaan perheen 
                  niin toivoessa. Jos haet paikkaa sisaruksille, jotka eivät 
                  vielä ole varhaiskasvatuksessa, kirjoita tieto hakemuksen 
                  Muut lisätiedot -kohtaan.
                </P>
                <P>
                  Täytä nämä tiedot vain, jos käytät sisarusperustetta, sekä 
                  valitse alla olevissa hakutoiveissa ensisijaiseksi toiveeksi 
                  sama varhaiskasvatusyksikkö, jossa lapsen sisarus on.
                </P>
              </>
            )
          } 
        },
        units: {
          serviceVoucherLink:
            'https://www.ouka.fi/oulu/palveluseteli/yksityisen-paivahoidon-palveluseteli'
        }
      },
      heading: {
        info: {
          DAYCARE: (
            <>
              <P>
                Hakemus on jätettävä viimeistään neljä kuukautta ennen kuin 
                tarvitsette paikan. Mikäli tarvitsette varhaiskasvatusta kiireellisesti 
                työn tai opiskelujen vuoksi, käsittelyaika on kaksi viikkoa 
                hakemuksen saapumisesta. 
              </P>
              <P>
                Saatte kirjallisen päätöksen varhaiskasvatuspaikasta 
                Suomi.fi-viestit -palveluun tai postitse, mikäli et ole 
                ottanut Suomi.fi-palvelua käyttöön. Päätös on nähtävillä 
                myös eVaka-palvelussa kohdassa Hakeminen - Päätökset.
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
          CLUB: (
            <>
              <P>
                Kerhopaikkaa voi hakea ympäri vuoden. Kerhohakemuksella voi
                hakea kunnallista tai palvelusetelillä tuettua kerhopaikkaa.
                Kirjallinen ilmoitus kerhopaikasta lähetään Suomi.fi-viestit
                -palveluun. Mikäli haluatte ilmoituksen sähköisenä
                tiedoksiantona, teidän tulee ottaa Suomi.fi-viestit -palvelu
                käyttöön. Palvelusta ja sen käyttöönotosta saatte lisätietoa{' '}
                <ExternalLink
                  text="https://www.suomi.fi/viestit"
                  href="https://www.suomi.fi/viestit"
                  newTab
                />
                . Mikäli ette ota Suomi.fi-viestit -palvelua käyttöön, ilmoitus
                kerhopaikasta lähetetään teille postitse. Paikka myönnetään
                yhdeksi toimintakaudeksi kerrallaan.
              </P>
              <P>
                Kerhohakemus kohdistuu yhdelle kerhon toimintakaudelle. Kyseisen
                kauden päättyessä hakemus poistetaan järjestelmästä.
              </P>
            </>
          )
        }
      },
      serviceNeed: {
        startDate: {
          instructions: (
            <>
              Toivottua aloituspäivää on mahdollista muuttaa myöhemmäksi siihen 
              saakka, kunnes palveluohjaus on ottanut hakemuksen käsittelyyn. 
              Tämän jälkeen toivotun aloituspäivän muutokset tehdään ottamalla 
              yhteyttä varhaiskasvatuksen palveluohjaukseen
              {customerContactText()}
            </>
          )
        },
        clubDetails: {
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
                viimeistään kaksi viikkoa ennen kuin tarve alkaa. 
                Hakemuksen liitteenä tulee olla selvitys työ- tai 
                opiskelupaikasta molemmilta samassa taloudessa asuvilta 
                huoltajilta.  Kahden viikon käsittelyaika alkaa siitä, 
                kun olemme vastaanottaneet hakemuksen tarvittavine 
                liitteineen. Jos et voi lisätä liitteitä hakemukselle 
                sähköisesti, lähetä ne sähköpostilla osoitteeseen{' '}
                <a href="mailto:varhaiskasvatus@ouka.fi">varhaiskasvatus@ouka.fi</a>
                {' '}tai postilla osoitteeseen 
                Varhaiskasvatuksen palveluohjaus, PL 75, 90015 Oulun kaupunki.
              </P>
            )
          }
        },
        shiftCare: {
          instructions:
            'Ilta- ja vuorohoidolla tarkoitetaan pääasiassa klo 6.00-18.00 ulkopuolella ja viikonloppuisin sekä ympärivuorokautisesti tapahtuvaa varhaiskasvatusta. Mikäli tarvitset ilta- tai vuorohoitoa, täsmennä tarvetta hakemuksen Muut lisätiedot -kohdassa.',
          message: {
            text: (
              <P fitted={true}>
                Ilta- ja vuorohoito on tarkoitettu lapsille, joiden molemmat 
                vanhemmat ovat vuorotyössä tai opiskelevat pääsääntöisesti 
                iltaisin ja/tai viikonloppuisin. Hakemuksen liitteenä tulee 
                olla selvitys vuorotyöstä tai iltaisin ja/tai viikonloppuisin 
                tapahtuvasta opiskelusta molemmilta samassa taloudessa asuvilta 
                huoltajilta. Jos et voi lisätä liitteitä hakemukselle 
                sähköisesti, lähetä ne sähköpostilla osoitteeseen{' '}
                <a href="mailto:varhaiskasvatus@ouka.fi">varhaiskasvatus@ouka.fi</a>
                {' '}tai postilla osoitteeseen 
                Varhaiskasvatuksen palveluohjaus, PL 75, 90015 Oulun kaupunki.
              </P>
            )
          },
          attachmentsMessage: {
            text: (
              <P fitted={true}>
                Ilta- ja vuorohoito on tarkoitettu lapsille, joiden molemmat 
                vanhemmat ovat vuorotyössä tai opiskelevat pääsääntöisesti 
                iltaisin ja/tai viikonloppuisin. Hakemuksen liitteenä tulee 
                olla selvitys vuorotyöstä tai iltaisin ja/tai viikonloppuisin 
                tapahtuvasta opiskelusta molemmilta samassa taloudessa asuvilta 
                huoltajilta. Jos et voi lisätä liitteitä hakemukselle 
                sähköisesti, lähetä ne sähköpostilla osoitteeseen{' '}
                <a href="mailto:varhaiskasvatus@ouka.fi">varhaiskasvatus@ouka.fi</a>
                {' '}tai postilla osoitteeseen 
                Varhaiskasvatuksen palveluohjaus, PL 75, 90015 Oulun kaupunki.
              </P>
            )
          }
        },
        assistanceNeedInstructions: {
          DAYCARE:
            'Lapsen tuen tarpeella tarkoitetaan sellaisten tukitoimien tarvetta, jotka on osoitettu asiantuntijalausunnoin. Tukitoimet toteutuvat lapsen arjessa osana varhaiskasvatuksen muuta toimintaa. Varhaiskasvatuksen erityisopettaja ottaa hakijaan yhteyttä, jotta lapsen tuen tarve voidaan ottaa huomioon varhaiskasvatuspaikkaa myönnettäessä.',
          CLUB: 'Jos lapsella on tuen tarve, Oulun varhaiskasvatuksesta otetaan yhteyttä hakemuksen jättämisen jälkeen.'
        },
        partTime: {
          true: 'Osapäiväinen'
        },
        dailyTime: {
          label: {
            DAYCARE: 'Palveluntarve'
          }
        }
      },
      contactInfo: {
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
            päivittynyt väestötietojärjestelmään. Varhaiskasvatus- ja
            palvelusetelipäätös sekä tieto avoimen varhaiskasvatuksen
            kerhopaikasta toimitetaan automaattisesti myös eri osoitteessa
            asuvalle väestötiedoista löytyvälle huoltajalle.
          </P>
        ),
        futureAddressInfo:
          'Oulun varhaiskasvatuksessa virallisena osoitteena pidetään väestötiedoista saatavaa osoitetta. Osoite väestötiedoissa muuttuu hakijan tehdessä muuttoilmoituksen postiin tai maistraattiin.'
      },
      fee: {
        info: {
          DAYCARE: (
            <P>
              Kunnallisen varhaiskasvatuksen asiakasmaksu ja palvelusetelin
              omavastuuosuus perustuu varhaiskasvatuksen asiakasmaksuista
              annettuun lakiin (Laki varhaiskasvatuksen asiakasmaksuista
              (1503/2016)). Asiakasmaksu määräytyy perheen koon, palveluntarpeen
              sekä bruttotulojen mukaan. Uusien asiakkaiden tulee täyttää
              asiakasmaksulomake ja toimittaa tarvittavat liitteet
              Varhaiskasvatuksen asiakasmaksuihin viimeistään kuukauden kuluessa
              hoidon alkamisesta.
            </P>
          )
        },
        links: (
          <P>
            Lisätietoa varhaiskasvatuksen asiakasmaksuista löydät{' '}
            <ExternalLink
              href="https://www.ouka.fi/oulu/paivahoito-ja-esiopetus/paivahoitomaksut"
              text="Oulun kaupungin sivuilta"
              newTab
            />
          </P>
        )
      },
      additionalDetails: {
        dietInfo: (
          <>
            Erityisruokavaliosta huoltaja toimittaa varhaiskasvatuspaikkaan
            lääkärin tai ravitsemusterapeutin täyttämän ja allekirjoittaman
            lomakkeen{' '}
            <ExternalLink
              href="https://www.ouka.fi/oulu/paivahoito-ja-esiopetus"
              text="Oulun kaupungin sivuilta"
              newTab
            />
            , joka on määräaikainen.
          </>
        )
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
    privacyPolicyLink:
      'https://www.ouka.fi/oulu/verkkoasiointi/tietosuoja-ja-rekisteriselosteet-kasvatus-ja-koulutus',
    sendFeedbackLink: 'https://e-kartta.ouka.fi/efeedback'
  },
  map: {
    mainInfo: `Tässä näkymässä voit hakea kartalta Oulun varhaiskasvatus-, esiopetus- ja kerhopaikkoja. Tietoa yksityisistä päiväkodeista löydät Oulun kaupungin nettisivuilta.`,
    privateUnitInfo: <></>,
    serviceVoucherLink:
      'https://www.ouka.fi/oulu/palveluseteli/yksityisen-paivahoidon-palveluseteli',
    searchPlaceholder: 'Esim. Ainolan päiväkoti'
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
    formDescription: (
      <>
        <P data-qa="income-formDescription-p1">
          Tuloselvitys liitteineen palautetaan kuukauden kuluessa
          varhaiskasvatuksen aloittamisesta. Maksu voidaan määrätä
          puutteellisilla tulotiedoilla korkeimpaan maksuun. Puutteellisia
          tulotietoja ei korjata takautuvasti oikaisuvaatimusajan jälkeen.
        </P>
        <P>
          Asiakasmaksu peritään päätöksen mukaisesta varhaiskasvatuksen
          alkamispäivästä lähtien.
        </P>
        <P>
          Asiakkaan on viipymättä ilmoitettava tulojen ja perhekoon muutoksista
          varhaiskasvutuksen asiakasmaksuihin. Viranomainen on tarvittaessa
          oikeutettu perimään varhaiskasvatusmaksuja myös takautuvasti.
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
  }
}

export default fi
