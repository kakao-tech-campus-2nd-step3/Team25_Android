package com.example.team25.data.network.dto

import com.example.team25.domain.ReservationStatus
import com.google.gson.annotations.SerializedName

data class ReservationDto(
    @SerializedName("managerId") val managerId: String,
    @SerializedName("reservationId") val reservationId: String,
    @SerializedName("reservationStatus") val reservationStatus: String,
    @SerializedName("departureLocation") val departureLocation: String,
    @SerializedName("arrivalLocation") val arrivalLocation: String,
    @SerializedName("reservationDate") val reservationDate: String,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("transportation") val transportation: String,
    @SerializedName("price") val price: Int
)
