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

    fun clearAllData(): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val deleteQuery = "DELETE FROM schedule"
            val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)
            preparedStatement.executeUpdate()

            "All schedule data cleared."
        } catch (e: Exception) {
            e.printStackTrace()
            "Error clearing schedule data."
        } finally {
            connection.close()
        }
    }

    fun deleteGivenLessonId(lessonID: Int): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val deleteQuery = "DELETE FROM schedule WHERE lessonID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)
            preparedStatement.setInt(1, lessonID)

            val rowsDeleted = preparedStatement.executeUpdate()

            if (rowsDeleted > 0) {
                "Entry/Entries with lesson ID $lessonID deleted successfully."
            } else {
                "Entry/Entries with lesson ID $lessonID not found."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error deleting entry/entries."
        } finally {
            connection.close()
        }
    }

    fun deleteGivenStudentId(studentID: Int): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val deleteQuery = "DELETE FROM schedule WHERE studentID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)
            preparedStatement.setInt(1, studentID)

            val rowsDeleted = preparedStatement.executeUpdate()

            if (rowsDeleted > 0) {
                "Entry/Entries with student ID $studentID deleted successfully."
            } else {
                "Entry/Entries with student ID $studentID not found."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error deleting entry/entries."
        } finally {
            connection.close()
        }
    }
}

