package com.kakaotech.team25.data.repository

import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.remote.AccompanyApiService
import com.kakaotech.team25.domain.model.AccompanyInfo
import com.kakaotech.team25.domain.repository.AccompanyRepository
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultAccompanyRepository @Inject constructor(
    private val accompanyApiService: AccompanyApiService,
) : AccompanyRepository {
    override fun getAccompanyFlow(reservationId: String): Flow<List<AccompanyInfo>> = flow {
        val result = accompanyApiService.getAccompanyInfo(reservationId)
        if (result is Result.Success) result.body?.data?.let { accompanyDtos ->
            emit(accompanyDtos.asDomain())
        } else emit(emptyList())
    }
}
