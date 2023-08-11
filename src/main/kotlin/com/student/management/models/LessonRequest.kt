package com.student.management.models

import java.sql.Timestamp

data class LessonRequest(
    val dateAndTime: Timestamp,
    val duration: Int
)