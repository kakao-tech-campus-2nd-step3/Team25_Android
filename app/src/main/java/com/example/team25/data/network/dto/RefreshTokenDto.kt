package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("refreshToken") val refreshToken: String,
)
