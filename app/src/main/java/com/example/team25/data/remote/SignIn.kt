package com.example.team25.data.remote

import com.example.team25.data.network.dto.AccountLoginDto
import com.example.team25.data.network.dto.RefreshTokenDto
import com.example.team25.data.network.dto.TokenDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignIn {
    @POST("/auth/kakao/login")
    suspend fun getSignIn(
        @Body accountLoginDto: AccountLoginDto,
    ): Response<TokenDto>

    @POST("/auth/refresh")
    suspend fun refreshToken(
        @Body refreshTokenDto: RefreshTokenDto,
    ): Response<TokenDto>
}
