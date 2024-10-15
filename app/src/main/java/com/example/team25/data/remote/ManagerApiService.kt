package com.example.team25.data.remote

import com.example.team25.data.network.calladapter.Result
import com.example.team25.data.network.dto.ServiceResponse
import com.example.team25.data.network.dto.ManagerDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ManagerApiService {
    @GET("/api/managers")
    suspend fun fetchManagers(
        @Query("date") date: String,
        @Query("region") region: String
    ): Result<ServiceResponse<List<ManagerDto>>>
}
