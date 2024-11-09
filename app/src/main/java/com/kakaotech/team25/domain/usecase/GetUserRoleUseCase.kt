package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.repository.MainRepository
import javax.inject.Inject

class GetUserRoleUseCase @Inject constructor(
    private val mainRepository: MainRepository
){
    suspend operator fun invoke(): String? {
        val userRoleDto = mainRepository.getRole()
        return userRoleDto?.userRole
    }
}
