package com.example.team25.domain.repository

import com.example.team25.data.network.dto.AccountLoginDto
import com.example.team25.data.network.dto.TokenDto

interface LoginRepository {
    suspend fun login(oauthAccessToken: String): TokenDto?
}
