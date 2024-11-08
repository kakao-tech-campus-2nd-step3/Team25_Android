package com.kakaotech.team25.data.remote

import com.kakaotech.team25.data.network.dto.WithdrawDto
import retrofit2.Response
import retrofit2.http.DELETE

interface UserService {
    @DELETE("/api/users/withdraw")
    suspend fun withdraw(): Response<WithdrawDto>
}
