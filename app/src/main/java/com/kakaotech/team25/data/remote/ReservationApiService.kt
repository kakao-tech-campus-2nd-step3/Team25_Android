package com.kakaotech.team25.data.remote

import com.kakaotech.team25.data.network.dto.ReservationDto
import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.network.dto.ReservationCancelDto
import com.kakaotech.team25.data.network.dto.ReserveDto
import com.kakaotech.team25.data.network.dto.ServiceResponse
import com.kakaotech.team25.data.network.response.ReserveResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ReservationApiService {
    @GET("/api/reservations")
    suspend fun getReservations(): Result<ServiceResponse<List<ReservationDto>>>

    @POST("/api/reservations")
    suspend fun reserve(
        @Body reserveDto: ReserveDto
    ): Response<ReserveResponse>

    @PATCH("/api/reservations/cancel/{reservation_id}")
    suspend fun cancelReservation(
        @Path("reservation_id") reservationId: String,
        @Body reservationCancelDto: ReservationCancelDto
    ): Result<ServiceResponse<ReservationDto>>

}
