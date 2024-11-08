package com.kakaotech.team25.domain.repository

import com.kakaotech.team25.domain.model.ReservationInfo
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    fun getReservationsFlow(): Flow<List<ReservationInfo>>
}
