package com.student.management.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.student.management.databases.DatabaseManager
import com.student.management.models.PaymentRequest
import io.micronaut.context.annotation.Context
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp

@Context
class PaymentService(private val databaseManager: DatabaseManager, private val scheduleService: ScheduleService) {
    private val objectMapper: ObjectMapper = ObjectMapper()
    data class PaymentData(
        val paymentId: Int,
        val paymentDate: Timestamp,
        val studentID: Int,
        val paymentAmount: Float
    )
    fun addPayment(paymentRequest: PaymentRequest): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val insertQuery = "INSERT INTO payments (PaymentDate, StudentID, PaymentAmount) VALUES (?, ?, ?)"
            val preparedStatement: PreparedStatement = connection.prepareStatement(insertQuery)

            preparedStatement.setTimestamp(1, paymentRequest.paymentDate)
            preparedStatement.setInt(2, paymentRequest.studentID)
            preparedStatement.setFloat(3, paymentRequest.paymentAmount)

            preparedStatement.executeUpdate()

            "Payment added successfully"
        } catch (e: Exception) {
            throw PaymentServiceException("Error adding payment data.", e)
        } finally {
            connection.close()
        }
    }
    fun getAllPayments(): List<PaymentData> {
        val connection: Connection = databaseManager.getConnection()

        val payments = mutableListOf<PaymentData>()

        try {
            val query = "SELECT * FROM payments"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)

            val resultSet: ResultSet = preparedStatement.executeQuery()

            while (resultSet.next()) {
                val paymentId = resultSet.getInt("id")
                val paymentDate = resultSet.getTimestamp("paymentDate")
                val studentID = resultSet.getInt("studentID")
                val paymentAmount = resultSet.getFloat("paymentAmount")

                val paymentData = PaymentData(
                    paymentId,
                    paymentDate,
                    studentID,
                    paymentAmount
                )

                payments.add(paymentData)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            throw PaymentServiceException("Error fetching payment data.", e)
        } finally {
            connection.close()
        }

        return payments
    }


    fun deleteAllPayments() {
        val connection: Connection = databaseManager.getConnection()

        try {
            val deleteQuery = "DELETE FROM payments"
            val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)
            preparedStatement.executeUpdate()
        } catch (e: Exception) {
            e.printStackTrace()
            throw PaymentServiceException("Error deleting payments.", e)
        } finally {
            connection.close()
        }
    }

    fun deletePayment(paymentID: Int): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val deleteQuery = "DELETE FROM payments WHERE id = ?"
            val preparedStatement: PreparedStatement = connection.prepareStatement(deleteQuery)
            preparedStatement.setInt(1, paymentID)

            val rowsDeleted = preparedStatement.executeUpdate()

            if (rowsDeleted > 0) {
                "Payment with ID $paymentID deleted successfully."
            } else {
                "Payment with ID $paymentID not found."
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error deleting payment."
        } finally {
            connection.close()
        }
    }

    fun deleteGivenStudentId(studentID: Int): String {
        val connection: Connection = databaseManager.getConnection()

        return try {
            val deleteQuery = "DELETE FROM payments WHERE studentID = ?"
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

