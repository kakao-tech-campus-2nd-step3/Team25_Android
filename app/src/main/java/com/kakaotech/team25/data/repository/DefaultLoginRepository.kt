package com.kakaotech.team25.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.kakaotech.team25.TokensProto.Tokens
import com.kakaotech.team25.data.network.dto.AccountLoginDto
import com.kakaotech.team25.data.network.dto.TokenDto
import com.kakaotech.team25.data.remote.SignIn
import com.kakaotech.team25.di.TokenDataStore
import com.kakaotech.team25.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.time.Instant
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
        val currentTime = Instant.now().epochSecond

        tokenDataStore.updateData { currentTokens ->
            currentTokens.toBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setAccessTokenExpiresIn(accessTokenExpiresIn)
                .setRefreshTokenExpiresIn(refreshTokenExpiresIn)
                .setLoginTime(currentTime)
                .build()
        }
    }

    override suspend fun getSavedTokens(): Tokens? {
        return tokenDataStore.data.firstOrNull()
    }

    override suspend fun logout() {
        tokenDataStore.updateData { currentTokens ->
            currentTokens.toBuilder()
                .clearAccessToken()
                .clearRefreshToken()
                .clearAccessTokenExpiresIn()
                .clearRefreshTokenExpiresIn()
                .clearLoginTime()
                .build()
        }
    }

}
