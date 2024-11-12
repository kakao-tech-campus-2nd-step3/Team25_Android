package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class AccompanyDto (
    @SerializedName("status") val status: String,
    @SerializedName("statusDate") val statusDate: LocalDateTime,
    @SerializedName("statusDescribe") val statusDescribe: String,
)
