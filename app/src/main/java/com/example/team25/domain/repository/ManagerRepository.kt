package com.example.team25.domain.repository

import com.example.team25.domain.model.ManagerDomain

interface ManagerRepository {
    suspend fun fetchManagers()

    suspend fun insertManagers(managers: List<ManagerDomain>)

    suspend fun getAllManagers(): List<ManagerDomain>

    suspend fun getManagersByName(name: String): List<ManagerDomain>

    suspend fun clearManagers()
}
