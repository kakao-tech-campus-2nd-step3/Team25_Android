package com.example.team25.domain.repository

import com.example.team25.domain.model.ManagerDomain
import kotlinx.coroutines.flow.Flow

interface ManagerRepository {
    val managersFlow: Flow<List<ManagerDomain>>

    suspend fun fetchManagers()

    suspend fun insertManagers(managers: List<ManagerDomain>)
}
