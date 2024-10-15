package com.example.team25.data.repository

import com.example.team25.data.dao.ManagerDao
import com.example.team25.data.entity.mapper.asDomainFromDto
import com.example.team25.data.entity.mapper.asDomainFromEntity
import com.example.team25.data.entity.mapper.asEntity
import com.example.team25.data.network.calladapter.Result.*
import com.example.team25.data.remote.ManagerApiService
import com.example.team25.domain.model.ManagerDomain
import com.example.team25.domain.repository.ManagerRepository
import javax.inject.Inject

class DefaultManagerRepository @Inject constructor(
    private val managerDao: ManagerDao,
    private val managerApiService: ManagerApiService
) : ManagerRepository {
    override suspend fun insertManagers(managers: List<ManagerDomain>) {
        return managerDao.insertManagers(managers.asEntity())
    }

    override suspend fun getAllManagers(): List<ManagerDomain> {
        return managerDao.getAllManagers().asDomainFromEntity()
    }

    override suspend fun getManagersByName(name: String): List<ManagerDomain> {
        return managerDao.getManagersByName(name).asDomainFromEntity()
    }

    override suspend fun clearManagers() {
        return managerDao.clearManagers()
    }

    override suspend fun fetchManagers() {
        val result = managerApiService.fetchManagers("2024-09-01", "Seoul")
        if (result is Success) result.body?.data?.let { managerDtos ->
            insertManagers(managerDtos.asDomainFromDto())
        }
    }
}
