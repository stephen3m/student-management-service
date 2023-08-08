package com.student.management.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.student.management.databases.DatabaseManager
import com.student.management.models.StudentRequest
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.micronaut.context.annotation.Context
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

@Context
class StudentService(private val databaseManager: DatabaseManager) {
    private val objectMapper: ObjectMapper = ObjectMapper()
    fun addStudent(studentRequest: StudentRequest): String {
        val connection: Connection = databaseManager.getConnection()

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
        }
    }

    fun readData(): List<Map<String, Any>> {
        val connection: Connection = databaseManager.getConnection()
        val results = mutableListOf<Map<String, Any>>()

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
        }

        return results
    }

    fun updateFirstName(
        @PathVariable studentId: Int,
        @Body updateRequest: StudentRequest
    ): String {
        val connection: Connection = databaseManager.getConnection()

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

    fun updateLastName(
        @PathVariable studentId: Int,
        @Body updateRequest: StudentRequest
    ): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val updateQuery = "UPDATE students SET LastName = ? WHERE StudentID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)

            preparedStatement.setString(1, updateRequest.lastName)
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

    fun clearAllData(): String {
        val connection: Connection = databaseManager.getConnection()

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
        }
    }
}

//import com.example.controllers.TestController
//import io.micronaut.context.annotation.Bean
//import io.micronaut.context.annotation.Factory
//import javax.sql.DataSource
//import com.zaxxer.hikari.HikariConfig
//import com.zaxxer.hikari.HikariDataSource
//import java.sql.Connection
//import java.sql.PreparedStatement
//import java.sql.ResultSet
//
//@Factory
//class StudentServiceFactory {
//
//    @Bean
//    fun dataSource(): DataSource {
//        val config = HikariConfig()
//        config.jdbcUrl = "jdbc:postgresql://localhost:5432/demo"
//        config.username = "demo"
//        config.password = "password"
//        config.driverClassName = "org.postgresql.Driver"
//        return HikariDataSource(config)
//    }
//}
//
//class StudentService(private val dataSource: DataSource) {
//    fun addStudent(studentRequest: TestController.StudentRequest): String {
//        val connection: Connection = dataSource.connection
//
//        return try {
//            val insertQuery = "INSERT INTO students (studentID, FirstName, LastName) VALUES (?, ?, ?)"
//            val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery)
//
//            preparedStatement.setInt(1, studentRequest.id)
//            preparedStatement.setString(2, studentRequest.firstName)
//            preparedStatement.setString(3, studentRequest.lastName)
//
//            preparedStatement.executeUpdate()
//
//            "Student data added successfully."
//        } catch (e: Exception) {
//            e.printStackTrace()
//            "Error adding student data."
//        } finally {
//            connection.close()
//        }
//    }
//
//    fun readData(): List<Map<String, Any>> {
//        val connection: Connection = dataSource.connection
//        val results = mutableListOf<Map<String, Any>>()
//
//        try {
//            val query = "SELECT * FROM students"
//            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
//            val resultSet: ResultSet = preparedStatement.executeQuery()
//
//            while (resultSet.next()) {
//                val id = resultSet.getInt("studentID")
//                val lastName = resultSet.getString("LastName")
//                val firstName = resultSet.getString("FirstName")
//
//                val resultMap = mapOf(
//                    "id" to id,
//                    "lastName" to lastName,
//                    "firstName" to firstName
//                )
//
//                results.add(resultMap)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            connection.close()
//        }
//
//        return results
//    }
//
//    fun clearAllData(): String {
//        val connection: Connection = dataSource.connection
//
//        return try {
//            val deleteQuery = "DELETE FROM students"
//            val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)
//            preparedStatement.executeUpdate()
//
//            "All student data cleared."
//        } catch (e: Exception) {
//            e.printStackTrace()
//            "Error clearing student data."
//        } finally {
//            connection.close()
//        }
//    }
//}
