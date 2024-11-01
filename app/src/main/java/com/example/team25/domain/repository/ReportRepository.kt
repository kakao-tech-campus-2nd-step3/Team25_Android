package com.example.team25.domain.repository

import com.example.team25.domain.model.Report
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun getReportFlow(reservationId: String): Flow<Report>
}
