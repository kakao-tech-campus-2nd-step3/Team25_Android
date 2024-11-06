package com.example.team25.domain.repository

interface MainRepository {
    suspend fun withdraw(): String?
}
