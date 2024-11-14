package com.kakaotech.team25.domain.repository

import com.kakaotech.team25.data.network.dto.ReservationCancelDto
import com.kakaotech.team25.data.network.dto.ReserveDto
import com.kakaotech.team25.domain.model.ReservationInfo
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    fun getReservationsFlow(): Flow<List<ReservationInfo>>

    suspend fun cancelReservation(reservationId: String, reservationCancelDto: ReservationCancelDto): Result<String>

    suspend fun reserve(reserveDto: ReserveDto): Result<String?>
}
