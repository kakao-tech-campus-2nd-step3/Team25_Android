package com.example.team25.data.entity.mapper

import com.example.team25.data.network.dto.ReportDto
import com.example.team25.domain.MedicineTime
import com.example.team25.domain.model.Report

object ReportMapper {
    fun asDomainFromDto(reportDto: ReportDto): Report {
        return Report(
                doctorSummary = reportDto.doctorSummary,
                frequency = reportDto.frequency,
                medicineTime = reportDto.medicinTime,
                timeOfDays = reportDto.timeOfDays
            )
    }
}

fun ReportDto.asDomainFromDto(): Report {
    return ReportMapper.asDomainFromDto(this)
}
