{
  /*
SPDX-FileCopyrightText: 2021 City of Oulu

SPDX-License-Identifier: LGPL-2.1-or-later
*/
}

import React from 'react'
import { EmployeeCustomizations } from 'lib-customizations/types'
import OuluLogo from './city-logo.svg'
import featureFlags from './featureFlags'

const customizations: EmployeeCustomizations = {
  appConfig: {},
  translations: {
    fi: {
      // override translations here
      common: {
        careTypeLabels: {
          preschool: 'Esiopetusta täydentävä toiminta'
        }
      },
      childInformation: {
        assistanceNeed: {
          fields: {
            // @ts-expect-error: Type 'Element' is not assignable to type 'string'
            capacityFactorInfo: (
              <ol style={{ margin: '0', padding: '0 1em' }}>
                <li>
                  Kaupungin päiväkodeissa kerroin merkitään integroidussa
                  varhaiskasvatusryhmässä oleville tehostettua tai erityistä
                  tukea tarvitseville lapsille ja missä tahansa ryhmässä
                  kotoutumisen tukea saaville lapsille. Kertoimen tallentaa
                  varhaiskasvatuksen erityisopettaja.
                </li>
                <li>
                  Mikäli ostopalvelu- tai palvelusetelipäiväkodissa olevalla
                  lapsella on tehostetun tai erityisen tuen tarve, voidaan
                  hänelle määritellä tuen kerroin. Päätöksen kertoimesta tekee
                  varhaiskasvatusjohtaja, varhaiskasvatuksen erityisopettajan
                  esityksen perusteella. Kertoimen tallentaa varhaiskasvatuksen
                  asiakaspalvelu.
                </li>
              </ol>
            ),
            bases: 'Tuen tarve'
          }
        },
        assistanceAction: {
          title: 'Tukitoimet ja tukipalvelut',
          fields: {
            actions: 'Tukitoimet ja tukipalvelut'
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
            REASON_1: 'Päiväkoti täynnä',
            REASON_2: 'Sisäilma tai muu rakenteellinen syy',
            REASON_3: 'Henkilökuntaa tilapäisesti vähennetty'
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
      }
    }
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
    'DAYCARE',
    'DAYCARE_PART_TIME',
    'TEMPORARY_DAYCARE',
    'PRESCHOOL_DAYCARE',
    'CLUB',
    'SCHOOL_SHIFT_CARE'
  ],
  placementPlanRejectReasons: ['REASON_1', 'REASON_2', 'REASON_3', 'OTHER'],
  unitProviderTypes: [
    'MUNICIPAL',
    'PRIVATE'
  ]
}

export default customizations
