package com.example.team25.data.network

import com.example.team25.data.network.dto.HospitalDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/local/search/keyword.json")
    suspend fun getSearchKeyword(
        @Header("Authorization") key: String,
        @Query("category_group_code") categoryGroupCode: String,
        @Query("query") query: String,
        @Query("size") size: Int = 15,
        @Query("page") page: Int = 1,
    ): Response<ResultSearchedKeyword>
}

data class ResultSearchedKeyword(
    val documents: List<HospitalDto>,
)
