package com.kakaotech.team25.data.network.services

import com.kakaotech.team25.BuildConfig
import com.kakaotech.team25.data.network.KakaoApi
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import com.kakaotech.team25.domain.model.HospitalDomain
import com.kakaotech.team25.ui.reservation.interfaces.SearchHospitalService
import javax.inject.Inject

class RemoteSearchHospitalService
    @Inject
    constructor(
        private val kakaoApi: KakaoApi,
    ) : SearchHospitalService {
        override suspend fun getSearchedResult(
            keyword: String,
            page: Int,
        ): List<HospitalDomain> {
            val response =
                kakaoApi.getSearchKeyword(
                    key = BuildConfig.KAKAO_REST_API_KEY,
                    categoryGroupCode = "HP8",
                    query = keyword,
                    size = 15,
                    page = page,
                )

            if (response.isSuccessful) {
                response.body()?.documents?.let { documents ->
                    return documents.asDomain()
                }
            }
            throw RuntimeException("API 요청 실패: ${response.errorBody()?.string()}")
        }
    }
