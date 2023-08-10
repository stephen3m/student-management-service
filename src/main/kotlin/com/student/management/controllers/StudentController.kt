package com.student.management.controllers

import io.micronaut.http.MediaType
import com.student.management.models.StudentRequest
import com.student.management.services.StudentService
import io.micronaut.context.annotation.Context
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

@Context
@Controller("/students")
class StudentController(private val studentService: StudentService) {
    @Post(consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun addStudent(@Body studentRequest: StudentRequest): String {
        return studentService.addStudent(studentRequest)
    }

    @Get
    fun readData(): List<Map<String, Any>> {
        return studentService.readData()
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