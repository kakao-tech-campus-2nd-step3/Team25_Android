package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.repository.ManagerRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetManagerNameUseCase @Inject constructor(
    private val managerRepository: ManagerRepository
) {
    suspend operator fun invoke(managerId: String): String? {
        return managerRepository.getManagerNameFlow(managerId).firstOrNull()
    }
}
