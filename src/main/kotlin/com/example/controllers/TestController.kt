package com.example.controllers

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.micronaut.http.MediaType
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.http.annotation.*

@Controller("/students")
class TestController {

    private val objectMapper: ObjectMapper = ObjectMapper()

    @Post(consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun addStudent(@Body studentRequest: StudentRequest): String {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/demo"
        config.username = "demo"
        config.password = "password"
        config.driverClassName = "org.postgresql.Driver"

        val dataSource = HikariDataSource(config)

        val connection: Connection = dataSource.connection

        return try {
            val insertQuery = "INSERT INTO students (studentID, FirstName, LastName) VALUES (?, ?, ?)"
            val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery)

            preparedStatement.setInt(1, studentRequest.id)
            preparedStatement.setString(2, studentRequest.firstName)
            preparedStatement.setString(3, studentRequest.lastName)

            preparedStatement.executeUpdate()

            val responseMessage = "Student data added successfully."
            objectMapper.writeValueAsString(mapOf("message" to responseMessage))
        } catch (e: Exception) {
            e.printStackTrace()
            val errorMessage = "Error adding student data."
            objectMapper.writeValueAsString(mapOf("error" to errorMessage))
        } finally {
            connection.close()
            dataSource.close()
        }
    }

    data class StudentRequest(
        val id: Int,
        val firstName: String,
        val lastName: String
    )

    @Get
    fun readData(): List<Map<String, Any>> {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/demo"
        config.username = "demo"
        config.password = "password"
        config.driverClassName = "org.postgresql.Driver"

        val dataSource = HikariDataSource(config)

        val results = mutableListOf<Map<String, Any>>()

        val connection: Connection = dataSource.connection

        try {
            val query = "SELECT * FROM students"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                val id = resultSet.getInt("studentID")
                val lastName = resultSet.getString("LastName")
                val firstName = resultSet.getString("FirstName")

                val resultMap = mapOf(
                    "id" to id,
                    "lastName" to lastName,
                    "firstName" to firstName
                )

                results.add(resultMap)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.close()
            dataSource.close()
        }

        return results
    }

//    @Get("/{studentId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    fun checkStudentExists(@PathVariable studentId: Int): String {
//        val config = HikariConfig()
//        config.jdbcUrl = "jdbc:postgresql://localhost:5432/demo"
//        config.username = "demo"
//        config.password = "password"
//        config.driverClassName = "org.postgresql.Driver"
//
//        val dataSource = HikariDataSource(config)
//
//        val connection: Connection = dataSource.connection
//
//        return try {
//            val query = "SELECT COUNT(*) FROM students WHERE studentID = ?"
//            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
//            preparedStatement.setInt(1, studentId)
//
//            val resultSet: ResultSet = preparedStatement.executeQuery()
//
//            resultSet.next()
//            val count = resultSet.getInt(1)
//
//            if (count > 0) {
//                objectMapper.writeValueAsString(mapOf("exists" to true))
//            } else {
//                objectMapper.writeValueAsString(mapOf("exists" to false))
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            objectMapper.writeValueAsString(mapOf("error" to "Error checking student ID."))
//        } finally {
//            connection.close()
//        }
//    }

    @Patch("/changeId/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun patchStudent(
        @PathVariable studentId: Int,
        @Body studentRequest: StudentRequest
    ): String {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/demo"
        config.username = "demo"
        config.password = "password"
        config.driverClassName = "org.postgresql.Driver"

        val dataSource = HikariDataSource(config)

        val connection: Connection = dataSource.connection

        return try {
            val updateQuery = "UPDATE students SET studentID = ? WHERE studentID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)

            preparedStatement.setInt(1, studentRequest.id)
            preparedStatement.setInt(2, studentId)

            preparedStatement.executeUpdate()

            val responseMessage = "Student ID updated successfully."
            objectMapper.writeValueAsString(mapOf("message" to responseMessage))
        } catch (e: Exception) {
            e.printStackTrace()
            val errorMessage = "Error updating student ID."
            objectMapper.writeValueAsString(mapOf("error" to errorMessage))
        } finally {
            connection.close()
        }
    }

    @Patch("/changeFirstName/{studentId}")
    fun updateFirstName(
        @PathVariable studentId: Int,
        @Body updateRequest: UpdateRequest
    ): String {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/demo"
        config.username = "demo"
        config.password = "password"
        config.driverClassName = "org.postgresql.Driver"

        val dataSource = HikariDataSource(config)

        val connection: Connection = dataSource.connection

        return try {
            val updateQuery = "UPDATE students SET FirstName = ? WHERE studentID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)

            preparedStatement.setString(1, updateRequest.firstName)
            preparedStatement.setInt(2, studentId)

            val rowsUpdated = preparedStatement.executeUpdate()

            if (rowsUpdated > 0) {
                "First name updated successfully."
            } else {
                "No rows updated."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error updating first name."
        } finally {
            connection.close()
        }
    }

    @Patch("/changeLastName/{studentId}")
    fun updateLastName(
        @PathVariable studentId: Int,
        @Body lastNameUpdate: UpdateRequest
    ): String {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/demo"
        config.username = "demo"
        config.password = "password"
        config.driverClassName = "org.postgresql.Driver"

        val dataSource = HikariDataSource(config)

        val connection: Connection = dataSource.connection

        return try {
            val updateQuery = "UPDATE students SET LastName = ? WHERE StudentID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)

            preparedStatement.setString(1, lastNameUpdate.lastName)
            preparedStatement.setInt(2, studentId)

            val rowsUpdated = preparedStatement.executeUpdate()

            if (rowsUpdated > 0) {
                "Last name updated successfully."
            } else {
                "Student with ID $studentId not found."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error updating last name."
        } finally {
            connection.close()
        }
    }

    @Put("/{studentId}")
    fun updateStudent(
        @PathVariable studentId: Int,
        @Body studentUpdate: UpdateRequest
    ): String {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/demo"
        config.username = "demo"
        config.password = "password"
        config.driverClassName = "org.postgresql.Driver"

        val dataSource = HikariDataSource(config)

        val connection: Connection = dataSource.connection

        return try {
            val updateQuery = "UPDATE students SET StudentID = ?, FirstName = ?, LastName = ? WHERE StudentID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)

            preparedStatement.setInt(1, studentUpdate.id)
            preparedStatement.setString(2, studentUpdate.firstName)
            preparedStatement.setString(3, studentUpdate.lastName)
            preparedStatement.setInt(4, studentId)

            val rowsUpdated = preparedStatement.executeUpdate()

            if (rowsUpdated > 0) {
                "Student updated successfully."
            } else {
                "Student with ID $studentId not found."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error updating student."
        } finally {
            connection.close()
        }
    }

    data class UpdateRequest(
        val id: Int,
        val firstName: String,
        val lastName: String
    )

    @Delete
    fun clearAllData(): String {
        val config = HikariConfig()
        config.jdbcUrl = "jdbc:postgresql://localhost:5432/demo"
        config.username = "demo"
        config.password = "password"
        config.driverClassName = "org.postgresql.Driver"

        val dataSource = HikariDataSource(config)

        val connection: Connection = dataSource.connection

        return try {
            val deleteQuery = "DELETE FROM students"
            val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)
            preparedStatement.executeUpdate()

            "All student data cleared."
        } catch (e: Exception) {
            e.printStackTrace()
            "Error clearing student data."
        } finally {
            connection.close()
            dataSource.close()
        }
    }
}