package com.example.team25.data.repository

import com.example.team25.data.entity.mapper.asDomainFromDto
import com.example.team25.data.network.calladapter.Result
import com.example.team25.data.remote.ReportApiService
import com.example.team25.domain.model.Report
import com.example.team25.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DefaultReportRepository @Inject constructor(
    private val reportApiService: ReportApiService
) : ReportRepository {
    override fun getReportFlow(reservationId: String): Flow<Report> = flow {
        val result = reportApiService.getReportInfo(reservationId)
        if (result is Result.Success) result.body?.data?.let { reservationInfo ->
            emit(reservationInfo.asDomainFromDto())
        }
    }
}
