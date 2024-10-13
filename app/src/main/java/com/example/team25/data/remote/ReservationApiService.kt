package com.example.team25.data.remote

import com.example.team25.data.network.dto.ReservationDto
import retrofit2.Response
import retrofit2.http.GET

interface ReservationApiService {
    @GET("/api/reservations")
    suspend fun fetchReservations(): Response<List<ReservationDto>>
}
