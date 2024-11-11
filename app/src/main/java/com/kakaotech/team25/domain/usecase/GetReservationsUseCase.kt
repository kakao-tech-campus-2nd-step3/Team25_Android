package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetReservationsUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(): List<ReservationInfo>? {
        return reservationRepository.getReservationsFlow().firstOrNull()
    }
}
