package com.kakaotech.team25.data.network.dto

import com.kakaotech.team25.domain.ReservationStatus
import com.google.gson.annotations.SerializedName
import com.kakaotech.team25.data.network.response.ReserveResponse
import com.kakaotech.team25.domain.model.Patient
import java.time.LocalDateTime

data class ReservationDto(
    @SerializedName("managerId") val managerId: String,
    @SerializedName("reservationId") val reservationId: String,
    @SerializedName("reservationStatus") val reservationStatus: ReservationStatus,
    @SerializedName("departureLocation") val departureLocation: String,
    @SerializedName("arrivalLocation") val arrivalLocation: String,
    @SerializedName("reservationDateTime") val reservationDate: LocalDateTime?,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("transportation") val transportation: String,
    @SerializedName("price") val price: Int,
    @SerializedName("patient") val patient: ReserveResponse.Patient
)
