package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class AccompanyDto (
    @SerializedName("status") val status: String,
    @SerializedName("latitude") val latitude: Double = 0.0,
    @SerializedName("longitude") val longitude: Double = 0.0,
    @SerializedName("statusDate") val statusDate: String,
    @SerializedName("statusDescribe") val statusDescribe: String,
)
