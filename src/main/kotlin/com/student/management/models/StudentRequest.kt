package com.student.management.models

data class StudentRequest(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val phoneNumber: String,
    val instrument: String
)