package com.kakaotech.team25.domain.repository

import com.kakaotech.team25.data.network.dto.ProfileDto
import com.kakaotech.team25.domain.model.ManagerDomain
import kotlinx.coroutines.flow.Flow

interface ManagerRepository {
    val managersFlow: Flow<List<ManagerDomain>>

    suspend fun fetchManagers(formattedDate: String, region: String)

    suspend fun insertManagers(managers: List<ManagerDomain>)

    suspend fun getProfile(managerId: String): Result<ProfileDto?>
}
