package com.kakaotech.team25.data.network.authenticator

import android.util.Log
import androidx.datastore.core.DataStore
import com.kakaotech.team25.TokensProto.Tokens
import com.kakaotech.team25.data.network.dto.RefreshTokenDto
import com.kakaotech.team25.data.remote.SignIn
import com.kakaotech.team25.di.TokenDataStore
import com.kakaotech.team25.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class HttpAuthenticator @Inject constructor(
    @TokenDataStore private val tokenDataStore: DataStore<Tokens>,
    private val signIn: SignIn,
    private val loginRepository: LoginRepository
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val refreshToken = getRefreshToken()
            if (refreshToken.isNullOrEmpty()) {
                Log.e("HttpAuthenticator", "리프레시 토큰이 없습니다.")
                null
            } else {
                val newAccessToken = refreshAccessToken(refreshToken)
                if (newAccessToken != null) {
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()
                } else {
                    Log.e("HttpAuthenticator", "리프레시 토큰 만료")
                    null
                }
            }
        }
    }

    private suspend fun getRefreshToken(): String? = withContext(Dispatchers.IO) {
        tokenDataStore.data.first().refreshToken
    }

    private suspend fun refreshAccessToken(refreshToken: String): String? {
        return try {
            val refreshTokenDto = RefreshTokenDto(refreshToken)
            val response = signIn.refreshToken(refreshTokenDto)
            if (response.isSuccessful) {
                response.body()?.let { tokenDto ->
                    loginRepository.saveTokens(
                        tokenDto.data.accessToken,
                        tokenDto.data.refreshToken,
                        tokenDto.data.expiresIn,
                        tokenDto.data.refreshTokenExpiresIn
                    )
                    tokenDto.data.accessToken
                }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("HttpAuthenticator", "토큰 갱신 오류", e)
            null
        }
    }

}
