package com.kakaotech.team25.data.repository

import android.util.Log
import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.network.dto.ManagerDto
import com.kakaotech.team25.data.network.dto.ProfileDto
import com.kakaotech.team25.data.network.dto.ServiceResponse
import com.kakaotech.team25.data.network.dto.ProfileData
import com.kakaotech.team25.data.network.dto.WorkingHour
import com.kakaotech.team25.data.remote.ManagerApiService
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class DefaultManagerRepositoryTest {

    private lateinit var managerApiService: ManagerApiService
    private lateinit var managerRepository: DefaultManagerRepository

    @Before
    fun setUp() {
        managerApiService = mockk()
        managerRepository = DefaultManagerRepository(managerApiService)
        mockLogClass()
    }

    @Test
    fun `매니저 리스트 조회 성공 시 매니저 데이터를 방출한다`() = runTest {
        // Given
        val formattedDate = "2024-11-12"
        val region = "서울"
        val managerDtoList = listOf(
            ManagerDto(
                managerId = "1",
                name = "홍길동",
                profileImage = "profile_image_url",
                career = "2012~2020 부산대학병원",
                comment = "성심성의껏 모시겠습니다."
            )
        )
        val serviceResponse = ServiceResponse(
            data = managerDtoList,
            message = "매니저 조회에 성공하였습니다.",
            status = true
        )

        coEvery { managerApiService.getManagers(formattedDate, region) } returns Result.Success(
            body = serviceResponse
        )

        // When
        val result = managerRepository.getManagersFlow(formattedDate, region).firstOrNull()

        // Then
        assertEquals(managerDtoList.asDomain(), result)
        coVerify { managerApiService.getManagers(formattedDate, region) }
    }

    @Test
    fun `매니저 리스트 조회 실패 시 null을 방출한다`() = runBlocking {
        // Given
        val formattedDate = "2024-11-12"
        val region = "서울"

        coEvery { managerApiService.getManagers(formattedDate, region) } returns Result.Failure(
            code = 400,
            error = " 날짜 형식이 옳지 않습니다."
        )

        // When
        val result = managerRepository.getManagersFlow(formattedDate, region).firstOrNull()

        // Then
        assertNull(result)
        coVerify { managerApiService.getManagers(formattedDate, region) }
    }

    @Test
    fun `매니저 이름 조회 성공 시 매니저 이름 정보를 방출한다`() = runTest {
        // Given
        val managerId = "1"
        val name = "홍길동"
        val profileDto = ProfileDto(
            status = true,
            message = "Profile loaded",
            data = ProfileData(
                name = name,
                profileImage = "profile_image_url",
                career = "10년 경력",
                comment = "경험이 풍부한 매니저입니다.",
                workingRegion = "서울",
                gender = "남성",
                workingHour = WorkingHour(
                    monStartTime = "09:00",
                    monEndTime = "18:00",
                    tueStartTime = "09:00",
                    tueEndTime = "18:00",
                    wedStartTime = "09:00",
                    wedEndTime = "18:00",
                    thuStartTime = "09:00",
                    thuEndTime = "18:00",
                    friStartTime = "09:00",
                    friEndTime = "18:00",
                    satStartTime = "10:00",
                    satEndTime = "14:00",
                    sunStartTime = "10:00",
                    sunEndTime = "14:00"
                )
            )
        )

        coEvery { managerApiService.getProfile(managerId) } returns Response.success(profileDto)

        // When
        val result = managerRepository.getManagerNameFlow(managerId).firstOrNull()

        // Then
        assertEquals(name, result)
        coVerify { managerApiService.getProfile(managerId) }
    }

    @Test
    fun `매니저 이름 조회 실패 시 null을 방출한다`() = runBlocking {
        // Given
        val managerId = "2"

        coEvery { managerApiService.getProfile(managerId) } returns Response.error(
            404,
            "존재하지 않는 매니저입니다.".toResponseBody()
        )

        // When
        val result = managerRepository.getManagerNameFlow(managerId).firstOrNull()

        // Then
        assertNull(result)
        coVerify { managerApiService.getProfile(managerId) }
    }

    @Test
    fun `매니저 프로필을 조회 성공 시 프로필 정보를 반환한다`() = runBlocking {
        // Given
        val managerId = "3"
        val name = "김영자"
        val profileDto = ProfileDto(
            status = true,
            message = "Profile loaded",
            data = ProfileData(
                name = name,
                profileImage = "profile_image_url",
                career = "16년 경력",
                comment = "경험이 풍부한 매니저입니다.",
                workingRegion = "부산",
                gender = "여성",
                workingHour = WorkingHour(
                    monStartTime = "09:00",
                    monEndTime = "18:00",
                    tueStartTime = "09:00",
                    tueEndTime = "18:00",
                    wedStartTime = "09:00",
                    wedEndTime = "18:00",
                    thuStartTime = "09:00",
                    thuEndTime = "18:00",
                    friStartTime = "09:00",
                    friEndTime = "18:00",
                    satStartTime = "10:00",
                    satEndTime = "14:00",
                    sunStartTime = "10:00",
                    sunEndTime = "14:00"
                )
            )
        )

        coEvery { managerApiService.getProfile(managerId) } returns Response.success(profileDto)

        // When
        val result = managerRepository.getProfile(managerId)

        // Then
        assert(result.isSuccess)
        assertEquals(profileDto, result.getOrNull())
        coVerify { managerApiService.getProfile(managerId) }
    }

    @Test
    fun `매니저 프로필 조회 실패 시 실패 결과를 반환한다`() = runBlocking {
        // Given
        val managerId = "4"

        coEvery { managerApiService.getProfile(managerId) } returns Response.error(
            404,
            "존재하지 않는 매니저입니다.".toResponseBody()
        )

        // When
        val result = managerRepository.getProfile(managerId)

        // Then
        assert(result.isFailure)
        coVerify { managerApiService.getProfile(managerId) }
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
