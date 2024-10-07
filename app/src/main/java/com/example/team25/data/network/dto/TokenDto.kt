package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class TokenDto(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: TokenData,
)

data class TokenData(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
)
