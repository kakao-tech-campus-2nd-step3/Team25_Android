package com.example.team25.data.network.authenticator

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.datastore.core.DataStore
import com.example.team25.TokensProto.Tokens
import com.example.team25.data.network.dto.RefreshTokenDto
import com.example.team25.data.remote.SignIn
import com.example.team25.di.TokenDataStore
import com.example.team25.domain.repository.LoginRepository
import com.example.team25.ui.login.LoginEntryActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class HttpAuthenticator @Inject constructor(
    private val application: Application,
    @TokenDataStore private val tokenDataStore: DataStore<Tokens>,
    private val signIn: SignIn,
    private val loginRepository: LoginRepository
) : Authenticator {
    private val mutex = Mutex()
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            mutex.withLock {
                val refreshToken = withContext(Dispatchers.IO) { tokenDataStore.data.first().refreshToken }

                if (refreshToken.isNullOrEmpty()) {
                    Log.e("HttpAuthenticator", "리프레시 토큰이 없습니다.")
                    redirectToLogin()
                    return@withLock null
                } else {
                    val newAccessToken = refreshAccessToken(refreshToken)
                    if (newAccessToken != null) {
                        response.request.newBuilder()
                            .header("Authorization", "Bearer $newAccessToken")
                            .build()
                    } else {
                        Log.e("HttpAuthenticator", "토큰 갱신 실패")
                        redirectToLogin()
                        null
                    }
                }
            }
        }
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

    private fun redirectToLogin() {
        val intent = Intent(application, LoginEntryActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        application.startActivity(intent)
    }
}
