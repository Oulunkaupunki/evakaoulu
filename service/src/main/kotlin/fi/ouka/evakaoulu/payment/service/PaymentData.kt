package fi.ouka.evakaoulu.payment.service

import fi.ouka.evakaoulu.invoice.service.InvoiceFieldName
import fi.ouka.evakaoulu.util.DataMapper
import fi.ouka.evakaoulu.util.FieldType

enum class PaymentFieldName {
}

class PaymentField(val field: PaymentFieldName, val fieldType: FieldType, val start: Int, val length: Int, val decimals: Int = 0)

val headerRowFields = listOf(
)

typealias PaymentData = DataMapper<PaymentFieldName>