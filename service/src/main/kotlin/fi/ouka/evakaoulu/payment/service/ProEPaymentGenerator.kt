// SPDX-FileCopyrightText: 2023-2025 City of Oulu
//
// SPDX-License-Identifier: LGPL-2.1-or-later

package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.daycare.CareType
import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.ouka.evakaoulu.util.FieldType
import fi.ouka.evakaoulu.util.FinanceDateProvider
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class ProEPaymentGenerator(
    private val paymentChecker: PaymentChecker,
    val financeDateProvider: FinanceDateProvider,
    val bicMapper: BicMapper,
) {
    data class Result(
        val sendResult: PaymentIntegrationClient.SendResult = PaymentIntegrationClient.SendResult(),
        val paymentString: String = "",
    )

    fun gatherPaymentData(payment: Payment): PaymentData {
        var paymentData = PaymentData()

        var paymentDateFormatterYYMMDD = DateTimeFormatter.ofPattern("yyMMdd")

        paymentData.setAlphanumericValue(PaymentFieldName.INTIME_COMPANY_ID, "20")
        paymentData.setAlphanumericValue(PaymentFieldName.PROVIDER_ID, payment.unit.providerId ?: "")
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_ID, payment.number.toString())
        paymentData.setNumericValue(PaymentFieldName.INVOICE_ACCEPTANCE, 1)
        paymentData.setAlphanumericValue(PaymentFieldName.VOUCHER_TYPE, "3B")
        paymentData.setNumericValue(PaymentFieldName.VOUCHER_NUMBER, payment.number?.toInt() ?: 0)
        paymentData.setAlphanumericValue(PaymentFieldName.VOUCHER_DATE, financeDateProvider.previousMonthLastDate())
        paymentData.setNumericValue(PaymentFieldName.INVOICE_TYPE, 1)
        paymentData.setAlphanumericValue(PaymentFieldName.ACCOUNT_SUGGESTION, "1")
        paymentData.setAlphanumericValue(PaymentFieldName.PERIOD, financeDateProvider.previousMonthYYMM())
        paymentData.setAlphanumericValue(
            PaymentFieldName.INVOICE_DATE,
            payment.paymentDate?.format(paymentDateFormatterYYMMDD) ?: LocalDate.now()
                .format(paymentDateFormatterYYMMDD),
        )
        paymentData.setAlphanumericValue(
            PaymentFieldName.DUE_DATE,
            payment.dueDate?.format(paymentDateFormatterYYMMDD) ?: LocalDate.now().format(paymentDateFormatterYYMMDD),
        )
        paymentData.setNumericValue(PaymentFieldName.INVOICE_SUM, payment.amount)
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_1, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CURRENCY, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CASHBOX_DATE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CASHBOX_SUM, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CASHBOX_MINUS, "")
        paymentData.setAlphanumericValue(PaymentFieldName.DEBT_ACCOUNT, "00002545")
        paymentData.setAlphanumericValue(PaymentFieldName.SI_DEBT_ACCOUNT, "")
        paymentData.setNumericValue(PaymentFieldName.KP_PURCHASE_ACCOUNT, 4335)
        paymentData.setAlphanumericValue(PaymentFieldName.SI_PURCHASE_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KP_CASHBOX_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SI_CASHBOX_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KP_OTHER_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SI_OTHER_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KP_KERO_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SI_KERO_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.STATS, "")
        val calcIdentifier =
            "1104" +
                if (payment.unit.careType.contains(CareType.FAMILY) or
                    payment.unit.careType.contains(CareType.GROUP_FAMILY)
                ) {
                    "372"
                } else {
                    "371"
                }
        paymentData.setAlphanumericValue(PaymentFieldName.CALC_IDENTIFIER, calcIdentifier)
        paymentData.setAlphanumericValue(PaymentFieldName.RESP_PERSON, "")
        paymentData.setAlphanumericValue(PaymentFieldName.FACTORING_NUMBER, "")
        paymentData.setAlphanumericValue(PaymentFieldName.MACHINE_REFERENCE_NUMBER, "")
        paymentData.setAlphanumericValue(PaymentFieldName.APPR_TARGET, "")
        paymentData.setAlphanumericValue(PaymentFieldName.ALPHABETICAL_NAME, "")
        paymentData.setAlphanumericValue(PaymentFieldName.NAME, "")
        paymentData.setAlphanumericValue(PaymentFieldName.ADDRESS1, "")
        paymentData.setAlphanumericValue(PaymentFieldName.ADDRESS2, "")
        paymentData.setAlphanumericValue(PaymentFieldName.POSTAL_NUMBER, "")
        paymentData.setAlphanumericValue(PaymentFieldName.COUNTRY, "")
        paymentData.setAlphanumericValue(PaymentFieldName.LANGUAGE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.COUNTRY_CODE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.BANK, "")
        paymentData.setAlphanumericValue(PaymentFieldName.BANK_ACCOUNT, payment.unit.iban.toString())
        paymentData.setAlphanumericValue(
            PaymentFieldName.NOTE,
            payment.unit.providerId.toString() + " " + payment.unit.name.toString(),
        )
        paymentData.setAlphanumericValue(PaymentFieldName.VAT_PERIOD, financeDateProvider.previousMonthYYMM())
        paymentData.setAlphanumericValue(PaymentFieldName.VAT_VAL, "0")
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_2, "")
        paymentData.setAlphanumericValue(PaymentFieldName.RECLAMATION_DATE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.PAYMENT_TERM, "")
        paymentData.setAlphanumericValue(PaymentFieldName.PAYMENT_MESSAGE, "1")
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_BANK_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KOM_TRANSFER_BAN, "0")
        paymentData.setAlphanumericValue(PaymentFieldName.VALUATION_BAN, "0")
        paymentData.setAlphanumericValue(PaymentFieldName.PAYMENT_TERM_CODE, "00")
        paymentData.setAlphanumericValue(PaymentFieldName.EURO_CODE, "1")
        paymentData.setAlphanumericValue(PaymentFieldName.PROVIDER_ADDITIONAL_KEY, "")
        paymentData.setAlphanumericValue(PaymentFieldName.APP_KEY, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SUBSTITUTE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.ADDITIONAL_INFO, "")
        paymentData.setAlphanumericValue(PaymentFieldName.BANKS_2_3, "")
        paymentData.setAlphanumericValue(PaymentFieldName.FACT_PROVIDER_ADD_KEY, "")
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_ID_ARCHIVE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.XML_ATTACHMENT_ID, "")
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_ID_2, "")
        paymentData.setAlphanumericValue(PaymentFieldName.PICTURE_FILE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.INT_REF_NRO, "")
        paymentData.setAlphanumericValue(PaymentFieldName.BANK_BIC_CODE, bicMapper.mapIban(payment.unit.iban ?: ""))
        paymentData.setAlphanumericValue(PaymentFieldName.SUBLEDGER, "")
        paymentData.setAlphanumericValue(PaymentFieldName.DOC_ID, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SUBST_DOC_ID, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CONSTRUCTION_KEY, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CONSTRUCTION_NUMBER, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CONTRACT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CREDIT_TARGET_2, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SUBSTITUTE_FIELD, "")
        paymentData.setAlphanumericValue(PaymentFieldName.BREAKDOWN_TYPE, "9")
        paymentData.setAlphanumericValue(
            PaymentFieldName.DESCRIPTION,
            payment.unit.providerId.toString() + " " + payment.unit.name.toString(),
        )
        paymentData.setAlphanumericValue(PaymentFieldName.SUB_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.VAT_CODE, "105")
        paymentData.setAlphanumericValue(PaymentFieldName.AMOUNT1, "")
        paymentData.setAlphanumericValue(PaymentFieldName.AMOUNT2, "")
        paymentData.setAlphanumericValue(PaymentFieldName.DELIVERY_PERIOD, financeDateProvider.previousMonthYYMM())
        paymentData.setAlphanumericValue(PaymentFieldName.VAT_BALANCE_SUM, "")
        paymentData.setAlphanumericValue(PaymentFieldName.ACCURUAL_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.ACCURUAL_PERIODS, "")
        paymentData.setAlphanumericValue(PaymentFieldName.ACCURUAL_START, "")
        paymentData.setAlphanumericValue(PaymentFieldName.FIXED_ASSET_ITEM, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KOM_TARGET_NAME, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KOM_TARGET_RESP_PERSON, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KOM_TARGET_GROUP, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KOM_TARGET_REM_GROUP, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KOM_TARGET_START_DATE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.APPROVER, "")
        paymentData.setAlphanumericValue(PaymentFieldName.APPROVE_DATE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.INSPECTOR, "")
        paymentData.setAlphanumericValue(PaymentFieldName.INSPECTOR_DATE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.ACCOUNT_REFERENCE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.ROW_NUMBER, "")
        paymentData.setAlphanumericValue(PaymentFieldName.EMPTY_FIELD, "")

        return paymentData
    }

    fun generateRow(
        fields: List<PaymentField>,
        paymentData: PaymentData,
    ): String {
        var result = StringBuilder("")

        fields.forEach {
            if (it.fieldType == FieldType.ALPHANUMERIC) {
                var value = paymentData.getAlphanumericValue(it.field) ?: ""
                result.append(value.take(it.length).padEnd(it.length))
            } else if (it.fieldType == FieldType.NUMERIC) {
                var value = paymentData.getNumericValue(it.field) ?: 0
                var stringValue = value.toString().padStart(it.length, '0')
                // all Evaka values seem to be Int so we can just pad
                // the decimal part with the correct number of zeroes
                result.append(stringValue.padEnd(it.length + it.decimals, '0'))
            } else if (it.fieldType == FieldType.MONETARY) {
                var value = paymentData.getNumericValue(it.field) ?: 0
                // if the value is non-zero it has been multiplied by 100 to already contain two decimals
                val decimals = if (value == 0) it.decimals else it.decimals - 2
                val length = if (value == 0) it.length else it.length + 2
                var stringValue = value.toString().padStart(length, '0')
                result.append(stringValue.padEnd(length + decimals, '0'))
            }
        }

        result.append("\n")

        return result.toString()
    }

    fun formatPayment(paymentData: PaymentData): String {
        var result = generateRow(headerRowFields, paymentData)
        result += generateRow(paymentRowFields, paymentData)
        return result
    }

    fun generatePayments(payments: List<Payment>): Result {
        var successList = mutableListOf<Payment>()
        var failedList = mutableListOf<Payment>()

        val (failed, succeeded) = payments.partition { payment -> paymentChecker.shouldFail(payment) }
        failedList.addAll(failed)

        var paymentString = ""
        succeeded.forEach {
            if (it.amount > 0) {
                val paymentData = gatherPaymentData(it)
                paymentString += formatPayment(paymentData)
            }
            successList.add(it)
        }

        return Result(PaymentIntegrationClient.SendResult(successList, failedList), paymentString)
    }
}
