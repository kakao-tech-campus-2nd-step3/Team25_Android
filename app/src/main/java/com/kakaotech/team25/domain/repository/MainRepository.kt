package com.kakaotech.team25.domain.repository


interface MainRepository {
    suspend fun withdraw(): String?
}
