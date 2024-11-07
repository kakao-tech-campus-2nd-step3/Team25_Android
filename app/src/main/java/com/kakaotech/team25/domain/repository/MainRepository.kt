package com.kakaotech.team25.domain.repository

import com.kakaotech.team25.data.network.dto.UserRole

interface MainRepository {
    suspend fun withdraw(): String?

    suspend fun getRole() : UserRole?
}
