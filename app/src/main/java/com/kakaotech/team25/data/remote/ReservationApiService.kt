package com.kakaotech.team25.data.remote

import com.kakaotech.team25.data.network.dto.ReservationDto
import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.network.dto.ServiceResponse
import retrofit2.http.GET

interface ReservationApiService {
    @GET("/api/reservations")
    suspend fun getReservations(): Result<ServiceResponse<List<ReservationDto>>>
}
