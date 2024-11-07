package com.kakaotech.team25.data.remote

import com.kakaotech.team25.data.network.dto.UserRoleDto
import com.kakaotech.team25.data.network.dto.WithdrawDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET

interface UserService {
    @DELETE("/api/users/withdraw")
    suspend fun withdraw(): Response<WithdrawDto>

    @GET("/api/users/me/role")
    suspend fun getRole(): Response<UserRoleDto>
}
