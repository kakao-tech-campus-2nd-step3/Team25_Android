package com.kakaotech.team25.data.remote

import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.network.dto.CoordinatesDto
import com.kakaotech.team25.data.network.dto.ServiceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoordinatesApiService {
    @GET("/api/coord/{reservation_id}")
    suspend fun getCoordinates(
        @Path("reservation_id") reservationId: String
    ): Result<ServiceResponse<CoordinatesDto>>
}
