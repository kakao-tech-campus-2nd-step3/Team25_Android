package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.repository.MainRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(): String? {
        return mainRepository.withdraw()
    }
}
