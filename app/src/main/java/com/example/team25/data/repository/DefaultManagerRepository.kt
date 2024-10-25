package com.example.team25.data.repository

import android.util.Log
import com.example.team25.data.dao.ManagerDao
import com.example.team25.data.entity.mapper.asDomain
import com.example.team25.data.entity.mapper.asEntity
import com.example.team25.data.remote.ManagerApiService
import com.example.team25.domain.model.ManagerDomain
import com.example.team25.domain.repository.ManagerRepository
import org.json.JSONObject
import javax.inject.Inject

class DefaultManagerRepository @Inject constructor(
    private val managerDao: ManagerDao,
    private val managerApiService: ManagerApiService
) : ManagerRepository {
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

    override suspend fun fetchManagers() {
        val response = managerApiService.fetchManagers()
        if (response.isSuccessful) {
            response.body()?.let { managers ->
                managerDao.insertManagers(managers)
            }
        } else {
            response.errorBody()?.let { errorBody ->
                val message = JSONObject(errorBody.string()).getString("message")
                Log.e("ManagerRepository", "failure: $message")
            }
        }
    }
}
