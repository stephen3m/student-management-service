package com.student.management.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.student.management.databases.DatabaseManager
import com.student.management.models.ScheduleRequest
import io.micronaut.context.annotation.Context
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

@Context
class ScheduleService(private val databaseManager: DatabaseManager) {
    private val objectMapper: ObjectMapper = ObjectMapper()
    fun addToSchedule(scheduleRequest: ScheduleRequest): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val insertQuery = "INSERT INTO schedule (studentID, lessonID) VALUES (?, ?)"
            val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery)

            preparedStatement.setInt(1, scheduleRequest.studentID)
            preparedStatement.setInt(2, scheduleRequest.lessonID)

            preparedStatement.executeUpdate()

            val responseMessage = "Added entry successfully to schedule."
            objectMapper.writeValueAsString(mapOf("message" to responseMessage))
        } catch (e: Exception) {
            e.printStackTrace()
            val errorMessage = "Error adding entry to schedule."
            objectMapper.writeValueAsString(mapOf("error" to errorMessage))
        } finally {
            connection.close()
        }
    }

    fun readData(): List<Map<String, Any>> {
        val connection: Connection = databaseManager.getConnection()
        val results = mutableListOf<Map<String, Any>>()

        try {
            val query = "SELECT * FROM schedule"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                val scheduleID = resultSet.getInt("scheduleID")
                val studentID = resultSet.getInt("studentID")
                val lessonID = resultSet.getInt("lessonID")

                val resultMap = mapOf(
                    "scheduleID" to scheduleID,
                    "studentID" to studentID,
                    "lessonID" to lessonID
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
}

