package com.student.management.models

import java.sql.Timestamp

data class PaymentRequest(
    val paymentDate: Timestamp,
    val studentID: Int,
    val paymentAmount: Float
)