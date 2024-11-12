package com.kakaotech.team25.data.remote

import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.network.dto.ReportDto
import com.kakaotech.team25.data.network.dto.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ReportApiService {
    @GET("/api/reports/{reservation_id}")
    suspend fun getReportInfo(
        @Path("reservation_id") reservationId: String
    ): Result<ServiceResponse<List<ReportDto>>>
}
