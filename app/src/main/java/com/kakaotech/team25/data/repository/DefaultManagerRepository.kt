package com.kakaotech.team25.data.repository

import com.kakaotech.team25.data.dao.ManagerDao
import com.kakaotech.team25.data.entity.mapper.asDomainFromDto
import com.kakaotech.team25.data.entity.mapper.asDomainFromEntity
import com.kakaotech.team25.data.entity.mapper.asEntity
import com.kakaotech.team25.data.network.calladapter.Result.*
import com.kakaotech.team25.data.remote.ManagerApiService
import com.kakaotech.team25.domain.model.ManagerDomain
import com.kakaotech.team25.domain.repository.ManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultManagerRepository @Inject constructor(
    private val managerDao: ManagerDao,
    private val managerApiService: ManagerApiService
) : ManagerRepository {
    override val managersFlow: Flow<List<ManagerDomain>> = getAllManagersFlow()

    private fun getAllManagersFlow(): Flow<List<ManagerDomain>> {
        return managerDao.getAllManagers().map { it.asDomainFromEntity() }
    }

    override suspend fun updateManagers(managers: List<ManagerDomain>) {
        return managerDao.updateManagers(managers.asEntity())
    }

    override suspend fun fetchManagers(formattedDate: String, region: String) {
        val result = managerApiService.fetchManagers(formattedDate, region)
        if (result is Success) result.body?.data?.let { managerDtos ->
            updateManagers(managerDtos.asDomainFromDto())
        }
    }
}
