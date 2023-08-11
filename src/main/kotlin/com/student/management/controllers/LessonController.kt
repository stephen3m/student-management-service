package com.student.management.controllers

import io.micronaut.http.MediaType
import com.student.management.models.LessonRequest
import com.student.management.services.LessonService
import io.micronaut.context.annotation.Context
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*

@Context
@Controller("/lessons")
class LessonController(private val lessonService: LessonService) {
    @Post(consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun addLesson(@Body lessonRequest: LessonRequest): String {
        return lessonService.addLesson(lessonRequest)
    }

    @Get
    fun readData(): List<Map<String, Any>> {
        return lessonService.readData()
    }

    @Patch("/changeDateAndTime/{lessonID}")
    fun updateDateAndTime(
        @PathVariable lessonID: Int,
        @Body updateRequest: LessonRequest
    ): String {
        return lessonService.updateDateAndTime(lessonID, updateRequest)
    }

    @Patch("/changeDuration/{lessonID}")
    fun updateDuration(
        @PathVariable lessonID: Int,
        @Body updateRequest: LessonRequest
    ): String {
        return lessonService.updateDuration(lessonID, updateRequest)
    }

    @Delete
    fun clearAllData(): String {
        return lessonService.clearAllData()
    }

    @Delete("/delete/{lessonID}")
    fun deleteLesson(@PathVariable lessonID: Int): HttpResponse<String> {
        val result = lessonService.deleteLesson(lessonID)
        return if (result.contains("successfully")) {
            HttpResponse.ok(result)
        } else {
            HttpResponse.status<String>(HttpStatus.NOT_FOUND).body(result)
        }
    }
}