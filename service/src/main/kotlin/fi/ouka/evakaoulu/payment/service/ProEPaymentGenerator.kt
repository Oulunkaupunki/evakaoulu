package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.ouka.evakaoulu.invoice.service.InvoiceFieldName
import fi.ouka.evakaoulu.util.FieldType
import fi.ouka.evakaoulu.util.FinanceDateProvider
import org.springframework.stereotype.Component

@Component
class ProEPaymentGenerator(private val paymentChecker: PaymentChecker, val financeDateProvider: FinanceDateProvider) {

    data class Result(
        val sendResult: PaymentIntegrationClient.SendResult = PaymentIntegrationClient.SendResult(),
        val paymentString: String = ""
    )

    fun gatherPaymentData(payment: Payment): PaymentData {
        var paymentData = PaymentData()

        paymentData.setAlphanumericValue(PaymentFieldName.INTIME_COMPANY_ID, "20")
        paymentData.setAlphanumericValue(PaymentFieldName.PROVIDER_ID, payment.unit.providerId ?: "")
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_ID, payment.number.toString())
        paymentData.setNumericValue(PaymentFieldName.INVOICE_ACCEPTANCE, 1)
        paymentData.setNumericValue(PaymentFieldName.VOUCHER_TYPE, 42)
        paymentData.setAlphanumericValue(PaymentFieldName.VOUCHER_NUMBER, payment.number.toString())
        paymentData.setAlphanumericValue(PaymentFieldName.VOUCHER_DATE, financeDateProvider.currentDateWithAbbreviatedYear())
        paymentData.setNumericValue(PaymentFieldName.INVOICE_TYPE, 1)
        paymentData.setAlphanumericValue(PaymentFieldName.ACCOUNT_SUGGESTION, "")
        // TODO how to compute this
        paymentData.setAlphanumericValue(PaymentFieldName.PERIOD, "2208")
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_DATE, payment.paymentDate.toString()) // TODO needs converting to "YYMMDD"
        paymentData.setAlphanumericValue(PaymentFieldName.DUE_DATE, payment.dueDate.toString()) // TODO needs converting to "YYMMDD"
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_SUM, payment.amount.toString()) //TODO check if this is correct type
        paymentData.setAlphanumericValue(PaymentFieldName.INVOICE_1, "") //TODO check if this is correct type
        paymentData.setAlphanumericValue(PaymentFieldName.CURRENCY, "EUR") //TODO make sure this value is correct one
        paymentData.setAlphanumericValue(PaymentFieldName.CASHBOX_DATE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CASHBOX_SUM, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CASHBOX_MINUS, "")
        paymentData.setAlphanumericValue(PaymentFieldName.DEBT_ACCOUNT, "00002545")
        paymentData.setAlphanumericValue(PaymentFieldName.SI_DEBT_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KP_PURCHASE_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SI_PURCHASE_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KP_CASHBOX_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SI_CASHBOX_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KP_OTHER_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SI_OTHER_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.KP_KERO_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SI_KERO_ACCOUNT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.STATS, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CALC_IDENTIFIER, "")
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
        paymentData.setAlphanumericValue(PaymentFieldName.BANK_ACCOUNT,payment.unit.iban.toString())
        paymentData.setAlphanumericValue(PaymentFieldName.NOTE, "")
        paymentData.setAlphanumericValue(PaymentFieldName.VAT_PERIOD, "1904") //TODO add value from mappings
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
        paymentData.setAlphanumericValue(PaymentFieldName.BANK_BIC_CODE, "NDEAFIHH") //TODO: add BIC adapter here!
        paymentData.setAlphanumericValue(PaymentFieldName.SUBLEDGER, "")
        paymentData.setAlphanumericValue(PaymentFieldName.DOC_ID, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SUBST_DOC_ID, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CONSTRUCTION_KEY, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CONSTRUCTION_NUMBER, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CONTRACT, "")
        paymentData.setAlphanumericValue(PaymentFieldName.CREDIT_TARGET_2, "")
        paymentData.setAlphanumericValue(PaymentFieldName.SUBSTITUTE_FIELD, "")

        return paymentData
    }

    fun generateRow(fields: List<PaymentField>, paymentData: PaymentData): String {
        var result = ""

        fields.forEach{
            if (it.fieldType == FieldType.ALPHANUMERIC) {
                var value = paymentData.getAlphanumericValue(it.field) ?: ""
                result = result + value.padEnd(it.length)
            }
            else if (it.fieldType == FieldType.NUMERIC) {
                var value = paymentData.getNumericValue(it.field) ?: 0
                var stringValue = value.toString().padStart(it.length, '0')
                // all Evaka values seem to be Int so we can just pad
                // the decimal part with the correct number of zeroes
                result = result + stringValue.padEnd(it.length + it.decimals, '0')
            }
            else if (it.fieldType == FieldType.MONETARY) {
                var value = paymentData.getNumericValue(it.field) ?: 0
                // if the value is non-zero it has been multiplied by 100 to already contain two decimals
                val decimals = if (value == 0) it.decimals else it.decimals - 2
                val length = if (value == 0) it.length else it.length + 2
                var stringValue = value.toString().padStart(length, '0')
                result = result + stringValue.padEnd(length + decimals, '0')
            }
        }

        result = result + "\n"

        return result

    }

    fun formatPayment(paymentData: PaymentData): String {

        var result = generateRow(headerRowFields, paymentData)
        return result
    }

    fun generatePayments(payments: List<Payment> ): Result {
        var successList = mutableListOf<Payment>()
        var failedList = mutableListOf<Payment>()

        val (failed, succeeded) = payments.partition { payment -> paymentChecker.shouldFail(payment) }
        failedList.addAll(failed)

        var paymentString = ""
        succeeded.forEach {
            // val paymentData = gatherPaymentData(it)
            // paymentString += formatPayment(paymentData)
            successList.add(it)
        }

        return Result(PaymentIntegrationClient.SendResult(successList, failedList), paymentString)
    }
}
