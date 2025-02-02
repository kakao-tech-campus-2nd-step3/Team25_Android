package com.kakaotech.team25.data.repository

import com.kakaotech.team25.data.network.calladapter.Result.*
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import android.util.Log
import com.kakaotech.team25.data.network.dto.ProfileDto
import com.kakaotech.team25.data.remote.ManagerApiService
import com.kakaotech.team25.domain.model.ManagerDomain
import com.kakaotech.team25.domain.repository.ManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultManagerRepository @Inject constructor(
    private val managerApiService: ManagerApiService
) : ManagerRepository {
    override fun getManagersFlow(formattedDate: String, region: String): Flow<List<ManagerDomain>> = flow {
        val result = managerApiService.getManagers(formattedDate, region)
        if (result is Success) result.body?.data?.let { managerDto ->
            emit(managerDto.asDomain())
        } else {
            emit(listOf())
        }
    }

    override fun getManagerNameFlow(managerId: String): Flow<String> = flow {
        try {
            val response = managerApiService.getProfile(managerId)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status == true) {
                    responseBody.data?.name?.let { emit(it) }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Unknown"
        }
    }

    override suspend fun getProfile(managerId: String): Result<ProfileDto?> {
        return try {
            val response = managerApiService.getProfile(managerId)
            Log.d("profile", response.toString())
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status == true) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Result.failure(Exception("Get profile failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
