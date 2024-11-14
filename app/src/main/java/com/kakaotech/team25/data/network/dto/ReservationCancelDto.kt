package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class ReservationCancelDto(
    @SerializedName("cancelReason") val cancelReason: String,
    @SerializedName("cancelDetail") val cancelDetail: String
)
