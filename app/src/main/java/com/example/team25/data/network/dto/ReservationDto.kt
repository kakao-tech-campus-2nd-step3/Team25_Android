package com.example.team25.data.network.dto

import com.example.team25.domain.model.Patient
import com.example.team25.domain.ReservationStatus
import com.google.gson.annotations.SerializedName

data class ReservationDto(
    @SerializedName("managerId") val managerId: String,
    @SerializedName("reservationStatus") val reservationStatus: ReservationStatus,
    @SerializedName("departure") val departure: String,
    @SerializedName("destination") val destination: String,
    @SerializedName("serviceDate") val serviceDate: String,
    @SerializedName("serviceType") val serviceType: String,
    @SerializedName("transportation") val transportation: String,
    @SerializedName("price") val price: Int,
    @SerializedName("patient") val patient: Patient,
)
