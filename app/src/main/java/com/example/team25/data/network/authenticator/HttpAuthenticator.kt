package com.example.team25.data.network.authenticator

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.team25.TokensProto.Tokens
import com.example.team25.data.network.dto.TokenData
import com.example.team25.data.network.dto.TokenDto
import com.example.team25.data.remote.SignIn
import com.example.team25.di.TokenDataStore
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
    @TokenDataStore private val tokenDataStore: DataStore<Tokens>
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // 401에러 발생 시(엑세스 토큰 만료, 권한 x)
        if (response.code == HTTP_UNAUTHORIZED) {
            return runBlocking {
                val mutex = Mutex()

                mutex.withLock {
                    val refreshToken = withContext(Dispatchers.IO) { tokenDataStore.data.first().refreshToken }
                    if (refreshToken.isNullOrEmpty()) {
                        // refresh 토큰이 존재하지 않을 때, 재 로그인 (미구현)
                        Log.e("HttpAuthenticator", "리프레시 토큰이 없습니다.")
                        null
                    } else {
                        // refresh 토큰이 존재할 때, 새 access 토큰 발급
                        val newTokenDto = refreshAccessToken(refreshToken)
                        if (newTokenDto != null) {
                            saveTokensToDataStore(newTokenDto.data.accessToken, newTokenDto.data.refreshToken)

                            response.request.newBuilder()
                                .header("Authorization", "Bearer ${newTokenDto.data.accessToken}")
                                .build()
                        } else {
                            Log.e("HttpAuthenticator", "토큰 갱신 실패")
                            null
                        }
                    }
                }
            }
        }
        return null
    }

    /**
     * 테스트 용 임시 메서드 입니다.
     */
    private suspend fun refreshAccessToken(refreshToken: String): TokenDto? =
        TokenDto(true, "test message", TokenData("access token", "refresh token"))

    /*private suspend fun refreshAccessToken(refreshToken: String): TokenDto? {
        val response = signIn.getRefreshToken(refreshToken)
        return if (response.isSuccessful) {
            response.body()?.also { tokenDto ->
                Log.d("HttpAuthenticator", "새로운 토큰으로 갱신됨")
            }
        } else {
            Log.e("HttpAuthenticator", "리프레시 토큰이 만료되었거나 유효하지 않습니다.")
            null
        }
    }*/

    private suspend fun saveTokensToDataStore(accessToken: String, refreshToken: String) {
        tokenDataStore.updateData { currentTokens ->
            currentTokens.toBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .build()
        }
    }

}
