package com.example.team25.domain.usecase

import com.example.team25.data.network.dto.AccountLoginDto
import com.example.team25.data.network.dto.TokenDto
import com.example.team25.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase
    @Inject
    constructor(
        private val loginRepository: LoginRepository,
    ) {
        suspend operator fun invoke(accountLoginDto: AccountLoginDto): TokenDto? {
            return loginRepository.login(accountLoginDto)
        }
    }
