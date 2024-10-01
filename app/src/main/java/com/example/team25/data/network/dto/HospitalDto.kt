package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class HospitalDto(
    @SerializedName("place_name") val name: String,
    @SerializedName("road_address_name") val address: String
)
