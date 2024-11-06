package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.data.network.dto.TokenDto
import com.kakaotech.team25.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase
    @Inject
    constructor(
        private val loginRepository: LoginRepository,
    ) {
        suspend operator fun invoke(oauthAccessToken: String): TokenDto? {
            return loginRepository.login(oauthAccessToken)
        }
    }
