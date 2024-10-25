package com.example.team25.data.remote

import com.example.team25.data.entity.ManagerEntity
import retrofit2.Response
import retrofit2.http.GET

interface ManagerApiService {
    @GET("/api/managers")
    suspend fun fetchManagers(): Response<List<ManagerEntity>>
}
