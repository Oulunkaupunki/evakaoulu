package fi.ouka.evakaoulu.payment.service

import fi.espoo.evaka.invoicing.domain.Payment
import fi.espoo.evaka.invoicing.domain.PaymentIntegrationClient
import fi.ouka.evakaoulu.util.FieldType
import org.springframework.stereotype.Component

@Component
class ProEPaymentGenerator(private val paymentChecker: PaymentChecker) {

    data class Result(
        val sendResult: PaymentIntegrationClient.SendResult = PaymentIntegrationClient.SendResult(),
        val paymentString: String = ""
    )

    fun gatherPaymentData(payment: Payment): PaymentData {
        var paymentData = PaymentData()

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
