package com.example.team25.data.repository

import com.example.team25.data.remote.UserService
import com.example.team25.domain.repository.MainRepository
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val userService: UserService
):MainRepository {

    override suspend fun withdraw(): String? {
        val response = userService.withdraw()
        return if (response.isSuccessful) {
            response.body()?.message
        } else {
            null
        }
    }
}
