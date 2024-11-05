package com.example.team25.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.team25.TokensProto.Tokens
import com.example.team25.data.network.dto.AccountLoginDto
import com.example.team25.data.network.dto.TokenDto
import com.example.team25.data.remote.SignIn
import com.example.team25.di.TokenDataStore
import com.example.team25.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultLoginRepository @Inject constructor(
    private val signIn: SignIn,
    @TokenDataStore private val tokenDataStore: DataStore<Tokens>
) : LoginRepository {
    override suspend fun login(oauthAccessToken: String): TokenDto? {
        val accountLoginDto = mapToAccountLoginDto(oauthAccessToken)

        val response = signIn.getSignIn(accountLoginDto)
        return if (response.isSuccessful) {
            response.body()?.let { tokenDto ->
                Log.d("testt", response.code().toString())
                Log.d("testt", response.body().toString())
                saveTokens(
                    tokenDto.data.accessToken,
                    tokenDto.data.refreshToken,
                    tokenDto.data.expiresIn,
                    tokenDto.data.refreshTokenExpiresIn
                )

                val savedTokens = tokenDataStore.data.first() // 확인용
                Log.d("testt", "AccessToken from DataStore: ${savedTokens.accessToken}")
                Log.d("testt", "RefreshToken from DataStore: ${savedTokens.refreshToken}")
                tokenDto
            }
        } else {
            null
        }
    }

    private fun mapToAccountLoginDto(oauthAccessToken: String): AccountLoginDto {
        return AccountLoginDto(oauthAccessToken = oauthAccessToken)
    }

    override suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
        accessTokenExpiresIn: Long,
        refreshTokenExpiresIn: Long
    ) {
        tokenDataStore.updateData { currentTokens ->
            currentTokens.toBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setAccessTokenExpiresIn(accessTokenExpiresIn)
                .setRefreshTokenExpiresIn(refreshTokenExpiresIn)
                .build()
        }
    }
}
