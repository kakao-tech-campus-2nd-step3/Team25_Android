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
                saveTokensToDataStore(tokenDto.data.accessToken, tokenDto.data.refreshToken)

                val savedTokens = tokenDataStore.data.first() // 확인용
                Log.d("testt", "AccessToken from DataStore: ${savedTokens.accessToken}")
                Log.d("testt", "RefreshToken from DataStore: ${savedTokens.refreshToken}")
                tokenDto
            }
        } else {
            null
//            if (response.code() == 401) {
//                Log.d("LoginRepository", "액세스 토큰 만료됨, 리프레시 토큰으로 갱신 시도")
//                val newTokenDto = refreshToken()
//                newTokenDto ?: run {
//                    Log.e("LoginRepository", "리프레시 토큰 만료 또는 오류")
//                }
//                newTokenDto
//            } else {
//                Log.e("LoginRepository", "로그인 실패: ${response.message()}")
//                null
//            }
        }
    }

    // 백엔드 api 구현중
//    private suspend fun refreshToken(): TokenDto? {
//        val refreshToken = Utils.getRefreshToken("")
//
//        if (refreshToken.isNullOrEmpty()) {
//            Log.e("LoginRepository", "리프레시 토큰이 없습니다.")
//            return null
//        }
//
//        val response = signIn.getRefreshToken(refreshToken)
//        return if (response.isSuccessful) {
//            response.body()?.let { tokenDto ->
//                Utils.setAccessToken(tokenDto.accessToken)
//                Utils.setRefreshToken(tokenDto.refreshToken)
//                Log.d("LoginRepository", "새로운 토큰으로 갱신됨")
//                tokenDto
//            }
//        } else {
//            Log.e("LoginRepository", "리프레시 토큰이 만료됨")
//            null
//        }
//    }

    private fun mapToAccountLoginDto(oauthAccessToken: String): AccountLoginDto {
        return AccountLoginDto(oauthAccessToken = oauthAccessToken)
    }

    private suspend fun saveTokensToDataStore(accessToken: String, refreshToken: String) {
        tokenDataStore.updateData { currentTokens ->
            currentTokens.toBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .build()
        }
    }
}
