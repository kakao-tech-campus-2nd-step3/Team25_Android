package com.kakaotech.team25.data.repository

import com.kakaotech.team25.data.network.calladapter.Result.*
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import com.kakaotech.team25.data.remote.ReservationApiService
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultReservationRepository @Inject constructor(
    private val reservationApiService: ReservationApiService
) : ReservationRepository {
    override fun getReservationsFlow(): Flow<List<ReservationInfo>> = flow{
        val result = reservationApiService.getReservations()
        if (result is Success) result.body?.data?.let { reservationDto ->
            emit(reservationDto.asDomain())
        }
    }
}
