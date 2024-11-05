package com.example.team25.domain.usecase

import com.example.team25.TokensProto.Tokens
import com.example.team25.domain.repository.LoginRepository
import javax.inject.Inject

class GetSavedTokensUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Tokens? {
        return loginRepository.getSavedTokens()
    }
}
