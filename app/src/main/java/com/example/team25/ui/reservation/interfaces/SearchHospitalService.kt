package com.example.team25.ui.reservation.interfaces

import com.example.team25.domain.HospitalDomain

interface SearchHospitalService {
    suspend fun getSearchedResult(
        keyword: String,
        page: Int,
    ): List<HospitalDomain>
}
