package com.example.team25.domain.repository

import com.example.team25.domain.model.AccompanyInfo
import kotlinx.coroutines.flow.Flow

interface AccompanyRepository {
    fun getAccompanyFlow(reservationId: String): Flow<AccompanyInfo>
}
