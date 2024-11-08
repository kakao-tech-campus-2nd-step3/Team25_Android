package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.data.network.dto.ProfileDto
import com.kakaotech.team25.domain.repository.ManagerRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val managerRepository: ManagerRepository
) {
    suspend operator fun invoke(managerId: String): ProfileDto? {
        return try {
            val result = managerRepository.getProfile(managerId)
            result.getOrNull()
        } catch (e: Exception) {
            null
        }
    }
}
