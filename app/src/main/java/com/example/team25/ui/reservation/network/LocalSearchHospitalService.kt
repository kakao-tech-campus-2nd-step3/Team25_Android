package com.example.team25.ui.reservation.network

import android.content.Context
import com.example.team25.database.HospitalDatabase
import com.example.team25.domain.HospitalDomain
import com.example.team25.ui.reservation.interfaces.SearchHospitalService

class LocalSearchHospitalService(context: Context) : SearchHospitalService {

    private val hospitalDao = HospitalDatabase.getInstance(context).hospitalDao()
    override suspend fun getSearchedResult(keyword: String, page: Int): List<HospitalDomain> {
        val pageSize = 15
        val offset = (page - 1) * pageSize
        return hospitalDao.searchHospitalsByName(keyword, pageSize, offset)
    }
}
