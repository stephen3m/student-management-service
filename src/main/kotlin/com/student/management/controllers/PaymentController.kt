package com.student.management.controllers

import io.micronaut.http.MediaType
import com.student.management.models.PaymentRequest
import com.student.management.services.PaymentService
import io.micronaut.context.annotation.Context
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import java.sql.Timestamp

@Context
@Controller("/payments")
class PaymentController(private val paymentService: PaymentService) {
    @Post(consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun addPayment(@Body paymentRequest: PaymentRequest): String {
        return paymentService.addPayment(paymentRequest)
    }

    @Get
    fun getAllPayments(): List<PaymentService.PaymentData> {
        return paymentService.getAllPayments()
    }

    @Delete
    fun deleteAllPayments(): HttpResponse<String> {
        paymentService.deleteAllPayments()
        return HttpResponse.ok("All payments deleted")
    }

    @Delete("/delete/{paymentID}")
    fun deletePayment(@PathVariable paymentID: Int): HttpResponse<String> {
        val result = paymentService.deletePayment(paymentID)
        return if (result.contains("successfully")) {
            HttpResponse.ok(result)
        } else {
            HttpResponse.status<String>(HttpStatus.NOT_FOUND).body(result)
        }
    }
}