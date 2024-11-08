package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.repository.ManagerRepository
import com.kakaotech.team25.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoadReservationsUseCase
@Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val managerRepository: ManagerRepository
) {
    suspend operator fun invoke(): List<ReservationInfo> {
        val reservations = reservationRepository.getReservationsFlow().first()
        val updatedReservations = reservations.map { reservation ->
            val managerName = managerRepository.getManagerNameFlow(reservation.managerId).first()
            reservation.copy(managerName = managerName)
        }

        return updatedReservations
    }
}
