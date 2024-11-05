package com.example.team25.domain.usecase

import com.example.team25.domain.repository.LoginRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke() {
        loginRepository.logout()
    }
}
