{
  /*
SPDX-FileCopyrightText: 2021 City of Oulu

SPDX-License-Identifier: LGPL-2.1-or-later
*/
}

import { EmployeeCustomizations } from 'lib-customizations/types'

import OuluLogo from './city-logo.svg'
import featureFlags from './featureFlags'

const customizations: EmployeeCustomizations = {
  appConfig: {},
  translations: {
    fi: {
      invoices: {
        buttons: {
          individualSendAlertText: ''
        }
      },
      // override translations here
      common: {
        careTypeLabels: {
          preschool: 'Esiopetusta täydentävä toiminta'
        }
      },
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
            actions: 'Tukitoimet'
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
          DAYCARE_PART_TIME: 'Osapäiväinen varhaiskasvatus​',
          TEMPORARY_DAYCARE: 'Tilapäinen kokopäiväinen varhaiskasvatus',
          PRESCHOOL_DAYCARE: 'Esiopetusta täydentävä varhaiskasvatus',
          CLUB: 'Kerho',
          SCHOOL_SHIFT_CARE: 'Koululaisten vuorohoito'
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
  assistanceMeasures: [],
  placementTypes: [
    'CLUB',
    'DAYCARE',
    'DAYCARE_PART_TIME',
    'TEMPORARY_DAYCARE',
    'TEMPORARY_DAYCARE_PART_DAY',
    'PRESCHOOL',
    'PRESCHOOL_DAYCARE',
    'PREPARATORY',
    'PREPARATORY_DAYCARE',
    'SCHOOL_SHIFT_CARE'
  ],
  absenceTypes: [
    'OTHER_ABSENCE',
    'PLANNED_ABSENCE',
    'SICKLEAVE',
    'PARENTLEAVE',
    'FORCE_MAJEURE',
    'FREE_ABSENCE'
  ],
  voucherValueDecisionTypes: [
    'NORMAL',
    'RELIEF_ACCEPTED',
    'RELIEF_PARTLY_ACCEPTED',
    'RELIEF_REJECTED'
  ],
  placementPlanRejectReasons: ['REASON_1', 'REASON_2', 'OTHER'],
  unitProviderTypes: ['MUNICIPAL', 'PRIVATE', 'PRIVATE_SERVICE_VOUCHER']
}

export default customizations
