package com.kakaotech.team25.domain.repository

import com.kakaotech.team25.data.network.dto.ReserveDto
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.ReservationStatus
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    val reservationsFlow: Flow<List<ReservationInfo>>

    suspend fun fetchReservations()

    suspend fun insertReservation(reservations :List<ReservationInfo>)

    suspend fun getReservationsByStatus(status: ReservationStatus): List<ReservationInfo>

    suspend fun reserve(reserveDto: ReserveDto): Result<String?>
}
