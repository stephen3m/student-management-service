package com.student.management.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.student.management.databases.DatabaseManager
import com.student.management.models.LessonRequest
import io.micronaut.context.annotation.Context
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

@Context
class LessonService(private val databaseManager: DatabaseManager) {
    private val objectMapper: ObjectMapper = ObjectMapper()
    fun addLesson(lessonRequest: LessonRequest): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val insertQuery = "INSERT INTO lessons (DateAndTime, Duration) VALUES (?, ?)"
            val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery)

            preparedStatement.setTimestamp(1, lessonRequest.dateAndTime)
            preparedStatement.setInt(2, lessonRequest.duration)

            preparedStatement.executeUpdate()

            val responseMessage = "Lesson data added successfully."
            objectMapper.writeValueAsString(mapOf("message" to responseMessage))
        } catch (e: Exception) {
            e.printStackTrace()
            val errorMessage = "Error adding lesson data."
            objectMapper.writeValueAsString(mapOf("error" to errorMessage))
        } finally {
            connection.close()
        }
    }

    fun readData(): List<Map<String, Any>> {
        val connection: Connection = databaseManager.getConnection()
        val results = mutableListOf<Map<String, Any>>()

        try {
            val query = "SELECT * FROM lessons"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                val id = resultSet.getInt("lessonID")
                val dateAndTime = resultSet.getTimestamp("DateAndTime")
                val duration = resultSet.getInt("Duration")

                val resultMap = mapOf(
                    "id" to id,
                    "dateAndTime" to dateAndTime,
                    "duration" to duration
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

    fun updateDateAndTime(
        @PathVariable lessonID: Int,
        @Body updateRequest: LessonRequest
    ): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val updateQuery = "UPDATE lessons SET dateAndTime = ? WHERE lessonID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)

            preparedStatement.setTimestamp(1, updateRequest.dateAndTime)
            preparedStatement.setInt(2, lessonID)

            val rowsUpdated = preparedStatement.executeUpdate()

            if (rowsUpdated > 0) {
                "Lesson's date and time updated successfully."
            } else {
                "No rows updated."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error updating lesson's date and time."
        } finally {
            connection.close()
        }
    }

    fun updateDuration(
        @PathVariable lessonID: Int,
        @Body updateRequest: LessonRequest
    ): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val updateQuery = "UPDATE lessons SET duration = ? WHERE lessonID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(updateQuery)

            preparedStatement.setInt(1, updateRequest.duration)
            preparedStatement.setInt(2, lessonID)

            val rowsUpdated = preparedStatement.executeUpdate()

            if (rowsUpdated > 0) {
                "Lesson's duration updated successfully."
            } else {
                "Lesson with ID $lessonID not found."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error updating lesson's duration."
        } finally {
            connection.close()
        }
    }

    fun clearAllData(): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val deleteQuery = "DELETE FROM lessons"
            val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)
            preparedStatement.executeUpdate()

            "All lesson data cleared."
        } catch (e: Exception) {
            e.printStackTrace()
            "Error clearing lesson data."
        } finally {
            connection.close()
        }
    }

    fun deleteLesson(lessonID: Int): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val deleteQuery = "DELETE FROM lessons WHERE lessonID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)
            preparedStatement.setInt(1, lessonID)

            val rowsDeleted = preparedStatement.executeUpdate()

            if (rowsDeleted > 0) {
                "Lesson with ID $lessonID deleted successfully."
            } else {
                "Lesson with ID $lessonID not found."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error deleting lesson."
        } finally {
            connection.close()
        }
    }
}

