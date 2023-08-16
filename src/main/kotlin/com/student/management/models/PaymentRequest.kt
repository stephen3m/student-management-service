package com.student.management.models

import java.sql.Timestamp

data class PaymentRequest(
    val paymentDate: Timestamp,
    val studentName: String,
    val paymentDollarAmount: Int,
    val paymentCentAmount: Int
)