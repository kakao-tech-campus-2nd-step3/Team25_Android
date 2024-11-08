package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class AccountLoginDto(
    @SerializedName("oauthAccessToken") val oauthAccessToken: String,
)
