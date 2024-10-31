package com.example.team25.data.remote

import com.example.team25.data.network.dto.ReservationDto
import com.example.team25.data.network.calladapter.Result
import com.example.team25.data.network.dto.ServiceResponse
import retrofit2.http.GET

interface ReservationApiService {
    @GET("/api/reservations")
    suspend fun fetchReservations(): Result<ServiceResponse<List<ReservationDto>>>
}
