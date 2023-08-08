package com.example.services
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
