package com.example.team25.data.repository

import android.util.Log
import com.example.team25.data.entity.mapper.asDomainFromDto
import com.example.team25.data.network.calladapter.Result
import com.example.team25.data.remote.AccompanyApiService
import com.example.team25.data.remote.CoordinatesApiService
import com.example.team25.domain.model.AccompanyInfo
import com.example.team25.domain.repository.AccompanyRepository
import com.kakao.vectormap.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultAccompanyRepository @Inject constructor(
    private val accompanyApiService: AccompanyApiService,
    private val coordinatesApiService: CoordinatesApiService
) : AccompanyRepository {
    override fun getAccompanyFlow(reservationId: String): Flow<List<AccompanyInfo>> = flow {
        val result = accompanyApiService.getAccompanyInfo(reservationId)
        if (result is Result.Success) result.body?.data?.let { accompanyDtos ->
            emit(accompanyDtos.asDomainFromDto())
        }
    }

    override fun getCoordinatesFlow(reservationId: String): Flow<LatLng> = flow {
        val result = coordinatesApiService.getCoordinates(reservationId)
        if (result is Result.Success) result.body?.data?.let { coords ->
            emit(LatLng.from(coords.latitude, coords.longitude))
        }
    }
}
