package com.kakaotech.team25.data.repository

import com.kakaotech.team25.data.network.calladapter.Result.*
import com.kakaotech.team25.data.network.dto.mapper.asDomain
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
        }
    }

    override fun getManagerNameFlow(managerId: String): Flow<String> = flow {
        val result = managerApiService.getManagerProfile(managerId)
        if (result is Success) result.body?.data?.let { managerName ->
            emit(managerName.name)
        }
    }
}
