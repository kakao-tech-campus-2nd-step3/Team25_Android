package com.kakaotech.team25.data.remote

import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.network.dto.AccompanyDto
import com.kakaotech.team25.data.network.dto.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AccompanyApiService {
    @GET("/api/tracking/{reservation_id}")
    suspend fun getAccompanyInfo(
        @Path("reservation_id") reservationId: String
    ): Result<ServiceResponse<List<AccompanyDto>>>
}
