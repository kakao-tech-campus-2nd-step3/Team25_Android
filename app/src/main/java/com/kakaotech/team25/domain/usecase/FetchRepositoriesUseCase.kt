package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.repository.ManagerRepository
import com.kakaotech.team25.domain.repository.ReservationRepository
import javax.inject.Inject

class FetchRepositoriesUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val managerRepository: ManagerRepository
) {
    suspend operator fun invoke() {
        reservationRepository.fetchReservations()
        managerRepository.fetchManagers()
    }
}
