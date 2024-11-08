package com.kakaotech.team25.domain.repository

import com.kakaotech.team25.domain.model.Report
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun getReportFlow(reservationId: String): Flow<Report>
}
