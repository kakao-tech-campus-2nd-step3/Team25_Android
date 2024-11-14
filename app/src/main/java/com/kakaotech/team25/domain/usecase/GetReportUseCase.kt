package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.model.Report
import com.kakaotech.team25.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReportUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(reservationId: String): Flow<Report> {
        return reportRepository.getReportFlow(reservationId)
    }
}
