package com.kakaotech.team25.data.repository

import android.util.Log
import com.kakaotech.team25.data.network.dto.UserRole
import com.kakaotech.team25.data.remote.UserService
import com.kakaotech.team25.domain.repository.MainRepository
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val userService: UserService
):MainRepository {

    override suspend fun withdraw(): String? {
        val response = userService.withdraw()
        Log.d("testt", response.toString())
        return if (response.isSuccessful) {
            response.body()?.message
        } else {
            null
        }
    }

    override suspend fun getRole(): UserRole? {
        val response = userService.getRole()
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }
}
