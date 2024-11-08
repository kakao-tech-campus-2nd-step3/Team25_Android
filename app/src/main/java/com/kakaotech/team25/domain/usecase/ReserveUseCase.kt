package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.model.toReserveDto
import com.kakaotech.team25.domain.repository.ReservationRepository
import javax.inject.Inject

class ReserveUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
){
    suspend operator fun invoke(reservationInfo: ReservationInfo): Result<String?> {
        return reservationRepository.reserve(reservationInfo.toReserveDto())
    }
}
