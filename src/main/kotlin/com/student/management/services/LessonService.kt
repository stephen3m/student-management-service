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
import java.sql.Timestamp

@Context
class LessonService(private val databaseManager: DatabaseManager, private val scheduleService: ScheduleService) {
    private val objectMapper: ObjectMapper = ObjectMapper()
    data class LessonData(
        val lessonID: Int,
        val dateAndTime: Timestamp,
        val duration: Int
    )

    fun addLesson(lessonRequest: LessonRequest): LessonData {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val insertQuery = "INSERT INTO lessons (DateAndTime, Duration) VALUES (?, ?) RETURNING LessonID"
            val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery)

            preparedStatement.setTimestamp(1, lessonRequest.dateAndTime)
            preparedStatement.setInt(2, lessonRequest.duration)

            val resultSet = preparedStatement.executeQuery()
            resultSet.next()
            val lessonID = resultSet.getInt("LessonID")

            LessonData(lessonID, lessonRequest.dateAndTime, lessonRequest.duration)
        } catch (e: Exception) {
            e.printStackTrace()
            throw LessonServiceException("Error adding lesson data.", e)
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

    fun getLessonDateAndTime(lessonID: Int): Timestamp? {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val query = "SELECT dateAndTime FROM lessons WHERE lessonID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, lessonID)

            val resultSet: ResultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                resultSet.getTimestamp("dateAndTime")
            } else {
                null // Lesson not found
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            connection.close()
        }
    }

    fun getLessonDuration(lessonID: Int): Int? {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val query = "SELECT duration FROM lessons WHERE lessonID = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, lessonID)

            val resultSet: ResultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                resultSet.getInt("duration")
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            connection.close()
        }
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
            scheduleService.deleteGivenLessonId(lessonID)
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

