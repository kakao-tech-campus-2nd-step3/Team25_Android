package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class HospitalDto(
    @SerializedName("id") val id: String,
    @SerializedName("place_name") val name: String,
    @SerializedName("road_address_name") val address: String,
)
