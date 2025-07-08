// SPDX-FileCopyrightText: 2023-2025 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.daycare.CareType
import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentStatus
import fi.espoo.evaka.invoicing.domain.PaymentUnit
import fi.espoo.evaka.shared.DaycareId
import fi.espoo.evaka.shared.EvakaUserId
import fi.espoo.evaka.shared.PaymentId
import fi.espoo.evaka.shared.domain.DateRange
import fi.espoo.evaka.shared.domain.HelsinkiDateTime
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

fun validPayment(): Payment {
    return Payment(
        PaymentId(UUID.randomUUID()),
        HelsinkiDateTime.Companion.of(LocalDate.of(2022, 8, 8), LocalTime.of(12, 0, 0)),
        HelsinkiDateTime.Companion.of(LocalDate.of(2022, 8, 8), LocalTime.of(12, 0, 0)),
        validPaymentUnit(),
        1234,
        DateRange(LocalDate.of(2022, 7, 1), LocalDate.of(2022, 7, 30)),
        657285,
        PaymentStatus.DRAFT,
        LocalDate.of(2022, 7, 31),
        LocalDate.of(2022, 8, 15),
        HelsinkiDateTime.Companion.of(LocalDate.of(2022, 7, 31), LocalTime.of(12, 0, 0)),
        EvakaUserId(UUID.randomUUID()),
    )
}

fun validPaymentUnit(): PaymentUnit {
    return PaymentUnit(
        DaycareId(UUID.randomUUID()),
        "Private test care provider",
        "1234567-8",
        "FI01 2345 6789 0123 4567 89",
        "PROVIDERID",
        "PARTNERCODE",
        setOf(CareType.CENTRE),
        "1234",
    )
}
