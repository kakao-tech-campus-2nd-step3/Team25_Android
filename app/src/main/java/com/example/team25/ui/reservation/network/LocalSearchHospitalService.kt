package com.example.team25.ui.reservation.network

import com.example.team25.dao.HospitalDao
import com.example.team25.domain.HospitalDomain
import com.example.team25.ui.reservation.interfaces.SearchHospitalService
import javax.inject.Inject

class LocalSearchHospitalService @Inject constructor(
    private val hospitalDao: HospitalDao
) : SearchHospitalService {

    override suspend fun getSearchedResult(keyword: String, page: Int): List<HospitalDomain> {
        val pageSize = 15
        val offset = (page - 1) * pageSize
        return hospitalDao.searchHospitalsByName(keyword, pageSize, offset)
    }
}
