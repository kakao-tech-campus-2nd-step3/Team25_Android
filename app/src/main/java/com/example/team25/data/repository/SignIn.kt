package com.example.team25.data.repository

import com.example.team25.data.network.dto.AccountLoginDto
import com.example.team25.data.network.dto.TokenDto
import com.example.team25.data.remote.SignIn
import com.example.team25.domain.repository.LoginRepository
import javax.inject.Inject

class DefaultLoginRepository @Inject constructor(
    private val signIn: SignIn
) : LoginRepository {
    override suspend fun login(accountLoginDto: AccountLoginDto): TokenDto? {
        val response = signIn.getSignIn(accountLoginDto)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
