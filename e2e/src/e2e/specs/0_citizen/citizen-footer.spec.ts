// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

import config from 'e2e-test/config'
import { test } from 'e2e-test/playwright'
import { waitUntilEqual } from 'e2e-test/utils'

test.describe('Citizen footer', () => {
  test('Oulu footer label', async ({ evaka: page }) => {
    await page.goto(config.enduserUrl)
    await waitUntilEqual(
      () => page.find('[data-qa="footer-citylabel"]').text,
      '© Oulun kaupunki'
    )
  })

  test('Oulu policy link', async ({ evaka: page }) => {
    await page.goto(config.enduserUrl)
    await waitUntilEqual(
      () => page.find('[data-qa="footer-policy-link"]').getAttribute('href'),
      'https://www.ouka.fi/tietosuoja/tietosuojaselosteet?registerId=1939220'
    )
  })

  test('Oulu feedback link', async ({ evaka: page }) => {
    await page.goto(config.enduserUrl)
    await waitUntilEqual(
      () => page.find('[data-qa="footer-feedback-link"]').getAttribute('href'),
      'https://palvelupyynto.siku.ouka.fi/customerui'
    )
  })
})
