package com.example.team25.data.repository

import com.example.team25.data.dao.ManagerDao
import com.example.team25.data.entity.mapper.asDomain
import com.example.team25.data.entity.mapper.asEntity
import com.example.team25.domain.model.ManagerDomain
import com.example.team25.domain.repository.ManagerRepository
import javax.inject.Inject

class DefaultManagerRepository @Inject constructor(
    private val managerDao: ManagerDao
): ManagerRepository {

    override suspend fun insertManagers(managers: List<ManagerDomain>) {
        return managerDao.insertManagers(managers.asEntity())
    }

    override suspend fun getAllManagers(): List<ManagerDomain> {
        return managerDao.getAllManagers().asDomain()
    }

    override suspend fun getManagersByName(name: String): List<ManagerDomain> {
        return managerDao.getManagersByName(name).asDomain()
    }

    override suspend fun clearManagers() {
        return managerDao.clearManagers()
    }
}
