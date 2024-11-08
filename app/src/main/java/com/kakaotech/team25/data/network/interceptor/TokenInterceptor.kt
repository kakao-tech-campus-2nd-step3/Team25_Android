package com.kakaotech.team25.data.network.interceptor

import androidx.datastore.core.DataStore
import com.kakaotech.team25.TokensProto.Tokens
import com.kakaotech.team25.di.TokenDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    @TokenDataStore private val tokenDataStore: DataStore<Tokens>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            val accessToken = withContext(Dispatchers.IO) {
                tokenDataStore.data.first().accessToken
            }

            val request = chain.request().newBuilder()
                .addHeader(AUTHORIZATION, "Bearer $accessToken")
                .build()

            chain.proceed(request)
        }
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}
