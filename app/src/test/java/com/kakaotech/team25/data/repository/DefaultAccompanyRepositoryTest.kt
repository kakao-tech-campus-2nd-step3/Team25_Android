package com.kakaotech.team25.data.repository

import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.network.dto.AccompanyDto
import com.kakaotech.team25.data.network.dto.ServiceResponse
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import com.kakaotech.team25.data.remote.AccompanyApiService
import com.kakaotech.team25.domain.model.AccompanyInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class DefaultAccompanyRepositoryTest {

    private lateinit var accompanyApiService: AccompanyApiService
    private lateinit var accompanyRepository: DefaultAccompanyRepository

    @Before
    fun setUp() {
        accompanyApiService = mockk()
        accompanyRepository = DefaultAccompanyRepository(accompanyApiService)
    }

    @Test
    fun `동행 정보 조회 성공 시 성공 메세지를 방출한다`() = runTest {
        // Given
        val reservationId = "1"
        val accompanyDtoList = listOf(
            AccompanyDto(
                status = "병원 이동",
                statusDate = LocalDateTime.of(2024, 11, 13, 21, 47),
                statusDescribe = "정기 검진을 위해 병원 방문 중입니다."
            ),
            AccompanyDto(
                status = "수납 및 서류 발급",
                statusDate = LocalDateTime.of(2024, 11, 13, 21, 47),
                statusDescribe = "수납 중입니다."
            )
        )
        val serviceResponse = ServiceResponse(
            data = accompanyDtoList,
            message = "실시간 동행현황이 조회되었습니다.",
            status = true
        )

        coEvery { accompanyApiService.getAccompanyInfo(reservationId) } returns Result.Success(
            body = serviceResponse
        )

        // When
        val result = accompanyRepository.getAccompanyFlow(reservationId).firstOrNull()

        // Then
        assertEquals(accompanyDtoList.asDomain(), result)
        coVerify { accompanyApiService.getAccompanyInfo(reservationId) }
    }

    @Test
    fun `동행 정보 조회 실패 시 빈 리스트 반환`() = runBlocking {
        // Given
        val reservationId = "2"

        coEvery { accompanyApiService.getAccompanyInfo(reservationId) } returns Result.Failure(
            code = 404,
            error = "존재하지 예약입니다."
        )

        // When
        val result = accompanyRepository.getAccompanyFlow(reservationId).firstOrNull()

        // Then
        assertEquals(emptyList<AccompanyInfo>(), result)
        coVerify { accompanyApiService.getAccompanyInfo(reservationId) }
    }
}
