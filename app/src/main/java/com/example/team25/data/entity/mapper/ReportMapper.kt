package com.example.team25.data.entity.mapper

import com.example.team25.data.network.dto.ReportDto
import com.example.team25.domain.MedicineTime
import com.example.team25.domain.model.Report

object ReportMapper {
    fun asDomainFromDto(reportDto: ReportDto): Report {
        return Report(
                doctorSummary = reportDto.doctorSummary,
                frequency = reportDto.frequency,
                medicineTime = when (reportDto.medicinTime) {
                    "BEFORE_MEAL" -> MedicineTime.BEFORE_MEAL
                    "AFTER_MEAL" -> MedicineTime.AFTER_MEAL
                    else -> MedicineTime.UNKNOWN
                },
                timeOfDays = reportDto.timeOfDays
            )
    }
}

fun ReportDto.asDomainFromDto(): Report {
    return ReportMapper.asDomainFromDto(this)
}
