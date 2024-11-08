package com.kakaotech.team25.data.entity.mapper

import com.kakaotech.team25.data.network.dto.ReportDto
import com.kakaotech.team25.domain.model.Report

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
