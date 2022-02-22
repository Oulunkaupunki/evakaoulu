// SPDX-FileCopyrightText: 2021 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

import config from 'e2e-test/config'
import { Page } from 'e2e-test/utils/page'
import { waitUntilEqual } from 'e2e-test/utils'

let page: Page

beforeEach(async () => {
  page = await Page.open()
  await page.goto(config.enduserUrl)
})
afterEach(async () => {
  await page.close()
})

describe('Citizen map page', () => {
  test('Has unit type filters for preschool, daycare and club', async () => {
    await page.find('[data-qa="map-filter-preschool"]').waitUntilVisible()
    await page.find('[data-qa="map-filter-daycare"]').waitUntilVisible()
    await page.find('[data-qa="map-filter-club"]').waitUntilVisible()
  })
  test('Has unit provider filters only for municipal and private', async () => {
    await page.find('[data-qa="map-filter-show-more"]').click()
    await page.find('[data-qa="map-filter-provider-municipal"]').waitUntilVisible()
    await page.find('[data-qa="map-filter-provider-private"]').waitUntilVisible()
    await page.find('[data-qa="map-filter-provider-purchased"]').waitUntilHidden()
    await page.find('[data-qa="map-filter-provider-private_service_voucher"]').waitUntilHidden()
  })
})
