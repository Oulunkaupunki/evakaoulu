// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

import config from 'e2e-test/config'
import { waitUntilEqual } from 'e2e-test/utils'
import { Page } from 'e2e-test/utils/page'

let page: Page

beforeEach(async () => {
  page = await Page.open()
  await page.goto(config.enduserUrl)
})
afterEach(async () => {
  await page.close()
})

describe('Citizen footer', () => {
  test('Oulu footer label', async () => {
    await waitUntilEqual(
      () => page.find('[data-qa="footer-citylabel"]').innerText,
      '© Oulun kaupunki'
    )
  })
  test('Oulu policy link', async () => {
    await waitUntilEqual(
      () => page.find('[data-qa="footer-policy-link"]').getAttribute('href'),
      'https://www.ouka.fi/oulu/verkkoasiointi/tietosuoja-ja-rekisteriselosteet-kasvatus-ja-koulutus'
    )
  })
  test('Oulu feedback link', async () => {
    await waitUntilEqual(
      () => page.find('[data-qa="footer-feedback-link"]').getAttribute('href'),
      'https://e-kartta.ouka.fi/efeedback'
    )
  })
})
