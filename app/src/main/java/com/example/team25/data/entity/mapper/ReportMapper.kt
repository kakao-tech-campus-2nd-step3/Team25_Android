package com.example.team25.data.entity.mapper

import com.example.team25.data.network.dto.ReportDto
import com.example.team25.domain.MedicinTime
import com.example.team25.domain.model.Report

object ReportMapper {
    fun asDomainFromDto(dto: List<ReportDto>): List<Report> {
        return dto.map { reportDto ->
            Report(
                doctorSummary = reportDto.doctorSummary,
                frequency = reportDto.frequency,
                medicinTime = when (reportDto.medicinTime) {
                    "BEFORE_MEAL" -> MedicinTime.BEFORE_MEAL
                    "AFTER_MEAL" -> MedicinTime.AFTER_MEAL
                    else -> MedicinTime.UNKNOWN
                },
                timeOfDays = reportDto.timeOfDays
            )
        }
    }
}

fun List<ReportDto>?.asDomainFromDto(): List<Report> {
    return ReportMapper.asDomainFromDto(this.orEmpty())
}
