package com.kakaotech.team25.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.kakaotech.team25.TokensProto.Tokens
import com.kakaotech.team25.data.network.dto.TokenData
import com.kakaotech.team25.data.network.dto.TokenDto
import com.kakaotech.team25.data.remote.SignIn
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class DefaultLoginRepositoryTest {
    private lateinit var signInService: SignIn
    private lateinit var tokenDataStore: DataStore<Tokens>
    private lateinit var loginRepository: DefaultLoginRepository

    @Before
    fun setUp() {
        signInService = mockk()
        tokenDataStore = mockk()

        loginRepository = DefaultLoginRepository(signInService, tokenDataStore)
        mockLogClass()
    }

    @Test
    fun `로그인 성공 시 토큰을 저장한다`() = runBlocking {
        // Given
        val oauthAccessToken = "sample_oauth_token"
        val tokenDto = TokenDto(
            true,
            "로그인을 성공했습니다",
            data = TokenData(
                accessToken = "access_token",
                refreshToken = "refresh_token",
                expiresIn = 3600,
                refreshTokenExpiresIn = 7200
            )
        )

        coEvery { signInService.getSignIn(any()) } returns Response.success(tokenDto)
        coEvery { tokenDataStore.updateData(any()) } returns Tokens.getDefaultInstance()
        coEvery { tokenDataStore.data } returns flowOf(Tokens.getDefaultInstance())

        // When
        val result = loginRepository.login(oauthAccessToken)

        // Then
        assertEquals("access_token", result?.data?.accessToken)
        coVerify { tokenDataStore.updateData(any()) }
    }

    @Test
    fun `로그인 실패 시 null을 반환한다`() = runBlocking {
        // Given
        val oauthAccessToken = "sample_oauth_token"

        coEvery { signInService.getSignIn(any()) } returns Response.error(400, "".toResponseBody())

        val result = loginRepository.login(oauthAccessToken)

        assertNull(result)
    }

    private fun mockLogClass() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }
}
