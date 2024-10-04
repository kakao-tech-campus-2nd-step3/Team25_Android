package com.example.team25.data.network.dto

data class TokenDto(
    val status: Boolean,
    val message: String,
    val data: TokenData,
)

data class TokenData(
    val accessToken: String,
    val refreshToken: String,
)
