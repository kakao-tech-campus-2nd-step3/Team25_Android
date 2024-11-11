package com.kakaotech.team25.ui.reservation.interfaces

import com.kakaotech.team25.domain.model.HospitalDomain

interface SearchHospitalService {
    suspend fun getSearchedResult(
        keyword: String,
        page: Int,
    ): List<HospitalDomain>
}
