package com.student.management.controllers

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.micronaut.http.MediaType
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import com.fasterxml.jackson.databind.ObjectMapper
import com.student.management.models.StudentRequest
import com.student.management.services.StudentService
import io.micronaut.context.annotation.Context
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
}