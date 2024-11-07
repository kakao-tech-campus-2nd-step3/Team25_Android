package com.kakaotech.team25.domain.repository

import com.kakaotech.team25.TokensProto
import com.kakaotech.team25.data.network.dto.TokenDto
import com.kakaotech.team25.data.network.dto.UserRole

interface LoginRepository {
    suspend fun login(oauthAccessToken: String): TokenDto?
    suspend fun saveTokens(accessToken: String, refreshToken: String, accessTokenExpiresIn: Long, refreshTokenExpiresIn: Long)

    suspend fun getSavedTokens(): TokensProto.Tokens?

    suspend fun logout()


}
