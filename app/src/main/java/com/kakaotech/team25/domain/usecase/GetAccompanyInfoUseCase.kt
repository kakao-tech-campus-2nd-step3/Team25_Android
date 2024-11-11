package com.kakaotech.team25.domain.usecase

import com.kakaotech.team25.domain.model.AccompanyInfo
import com.kakaotech.team25.domain.repository.AccompanyRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetAccompanyInfoUseCase @Inject constructor(
    private val accompanyRepository: AccompanyRepository
) {
    suspend operator fun invoke(reservationId: String): List<AccompanyInfo>? {
        return accompanyRepository.getAccompanyFlow(reservationId).firstOrNull()?.sortedBy { it.statusDate }
    }
}
