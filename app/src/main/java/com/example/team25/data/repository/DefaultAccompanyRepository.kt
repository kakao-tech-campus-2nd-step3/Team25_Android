package com.example.team25.data.repository

import com.example.team25.data.entity.mapper.asDomainFromDto
import com.example.team25.data.network.calladapter.Result
import com.example.team25.data.remote.AccompanyApiService
import com.example.team25.domain.model.AccompanyInfo
import com.example.team25.domain.repository.AccompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultAccompanyRepository @Inject constructor(
    private val accompanyApiService: AccompanyApiService
): AccompanyRepository {
    override fun getAccompanyFlow(reservationId: String): Flow<AccompanyInfo> = flow {
        val result = accompanyApiService.getAccompanyInfo(reservationId)
        if (result is Result.Success) result.body?.data?.let {accompanyDto ->
            emit(accompanyDto.asDomainFromDto())
        }
    }
}
