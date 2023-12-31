package com.student.management.controllers

import io.micronaut.http.MediaType
import com.student.management.models.StudentRequest
import com.student.management.services.StudentService
import com.student.management.services.PaymentService
import io.micronaut.context.annotation.Context
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

@Context
@Controller("/students")
class StudentController(private val studentService: StudentService, private val paymentService: StudentService) {
    @Post(consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun addStudent(@Body studentRequest: StudentRequest): String {
        return studentService.addStudent(studentRequest)
    }

    @Get
    fun readData(): List<Map<String, Any>> {
        return studentService.readData()
    }

    @Get("/getName/{studentID}")
    fun getStudentName(@PathVariable studentID: Int): String {
        return studentService.getStudentName(studentID)
    }

    @Patch("/changeFirstName/{studentId}")
    fun updateFirstName(
        @PathVariable studentId: Int,
        @Body updateRequest: StudentRequest
    ): String {
        return studentService.updateFirstName(studentId, updateRequest)
    }

    @Patch("/changeLastName/{studentId}")
    fun updateLastName(
        @PathVariable studentId: Int,
        @Body updateRequest: StudentRequest
    ): String {
        return studentService.updateLastName(studentId, updateRequest)
    }

    @Patch("/changeAge/{studentId}")
    fun updateAge(
        @PathVariable studentId: Int,
        @Body updateRequest: StudentRequest
    ): String {
        return studentService.updateAge(studentId, updateRequest)
    }

    @Patch("/changePhoneNumber/{studentId}")
    fun updatePhoneNumber(
        @PathVariable studentId: Int,
        @Body updateRequest: StudentRequest
    ): String {
        return studentService.updatePhoneNumber(studentId, updateRequest)
    }

    @Patch("/changeInstrument/{studentId}")
    fun updateInstrument(
        @PathVariable studentId: Int,
        @Body updateRequest: StudentRequest
    ): String {
        return studentService.updateInstrument(studentId, updateRequest)
    }

    @Delete
    fun clearAllData(): String {
        return studentService.clearAllData()
    }

    @Delete("/delete/{studentId}")
    fun deleteStudent(@PathVariable studentId: Int): HttpResponse<String> {
        val result = studentService.deleteStudent(studentId)
        return if (result.contains("successfully")) {
            HttpResponse.ok(result)
        } else {
            HttpResponse.status<String>(HttpStatus.NOT_FOUND).body(result)
        }
    }
}