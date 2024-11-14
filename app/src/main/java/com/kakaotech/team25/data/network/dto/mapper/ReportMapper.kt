package com.kakaotech.team25.data.network.dto.mapper

import com.kakaotech.team25.data.network.dto.ReportDto
import com.kakaotech.team25.domain.model.Report

object ReportMapper {
    fun asDomain(reportDto: ReportDto): Report {
        return Report(
                doctorSummary = reportDto.doctorSummary,
                frequency = reportDto.frequency,
                medicineTime = reportDto.medicineTime,
                timeOfDays = reportDto.timeOfDays
            )
    }
}

fun ReportDto.asDomain(): Report {
    return ReportMapper.asDomain(this)
}
