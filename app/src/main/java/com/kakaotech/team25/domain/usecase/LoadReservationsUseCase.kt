package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.model.ManagerDomain
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
        val reservations = reservationRepository.reservationsFlow.first()
        val managers = managerRepository.managersFlow.first()
        val managerMapById = managers.associateBy { it.managerId }

        return mapManagerNameToReservations(reservations, managerMapById)
    }

    private fun mapManagerNameToReservations(
        reservations: List<ReservationInfo>,
        managerMapById: Map<String, ManagerDomain>
    ): List<ReservationInfo> {
        return reservations.map { reservationInfo ->
            val managerName = managerMapById[reservationInfo.managerId]?.name ?: ""
            reservationInfo.copy(managerName = managerName)
        }
    }
}
