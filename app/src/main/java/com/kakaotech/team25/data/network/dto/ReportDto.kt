package com.kakaotech.team25.data.network.dto

import com.kakaotech.team25.domain.MedicineTime
import com.google.gson.annotations.SerializedName

data class ReportDto(
    @SerializedName("doctorSummary") val doctorSummary: String?,
    @SerializedName("frequency") val frequency: Int?,
    @SerializedName("medicinTime") val medicinTime: MedicineTime?,
    @SerializedName("timeOfDays") val timeOfDays: String?
)
