package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.data.network.dto.ReservationCancelDto
import com.kakaotech.team25.domain.repository.ReservationRepository
import javax.inject.Inject

class CancelReservationUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(reservationId: String, reservationCancelDto: ReservationCancelDto): Result<String> {
        return reservationRepository.cancelReservation(reservationId, reservationCancelDto)
    }
}
