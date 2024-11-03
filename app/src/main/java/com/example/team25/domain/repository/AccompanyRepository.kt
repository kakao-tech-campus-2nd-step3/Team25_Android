package com.example.team25.domain.repository

import com.example.team25.domain.model.AccompanyInfo
import com.kakao.vectormap.LatLng
import kotlinx.coroutines.flow.Flow

interface AccompanyRepository {
    fun getAccompanyFlow(reservationId: String): Flow<List<AccompanyInfo>>

    fun getCoordinatesFlow(reservationId: String): Flow<LatLng>
}
