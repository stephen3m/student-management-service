package com.student.management.controllers

import io.micronaut.http.MediaType
import com.student.management.models.ScheduleRequest
import com.student.management.services.ScheduleService
import io.micronaut.context.annotation.Context
import io.micronaut.http.annotation.*

@Context
@Controller("/schedule")
class ScheduleController(private val scheduleService: ScheduleService) {
    @Post(consumes = [MediaType.APPLICATION_JSON], produces = [MediaType.APPLICATION_JSON])
    fun addToSchedule(@Body scheduleRequest: ScheduleRequest): String {
        return scheduleService.addToSchedule(scheduleRequest)
    }

    @Get
    fun readData(): List<Map<String, Any>> {
        return scheduleService.readData()
    }
}