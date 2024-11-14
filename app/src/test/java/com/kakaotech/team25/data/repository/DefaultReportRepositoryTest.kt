package com.kakaotech.team25.data.repository

import android.util.Log
import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.network.dto.ReportDto
import com.kakaotech.team25.data.network.dto.ServiceResponse
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import com.kakaotech.team25.data.remote.ReportApiService
import com.kakaotech.team25.domain.MedicineTime
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultReportRepositoryTest {

    private lateinit var reportApiService: ReportApiService
    private lateinit var reportRepository: DefaultReportRepository

    @Before
    fun setUp() {
        reportApiService = mockk()
        reportRepository = DefaultReportRepository(reportApiService)
        mockLogClass()
    }

    @Test
    fun `리포트 정보 조회 성공 시 리포트 정보를 방출한다`() = runTest {
        // Given
        val reservationId = "1"
        val reportDtoList = listOf(
            ReportDto(
                doctorSummary = "환자 상태 양호",
                frequency = 3,
                medicineTime = MedicineTime.AFTER_MEAL,
                timeOfDays = "아침 점심"
            )
        )
        val serviceResponse = ServiceResponse(
            data = reportDtoList,
            message = "Success",
            status = true
        )

        coEvery { reportApiService.getReportInfo(reservationId) } returns Result.Success(
            body = serviceResponse
        )

        // When
        val result = reportRepository.getReportFlow(reservationId).firstOrNull()

        // Then
        assertEquals(reportDtoList.first().asDomain(), result)
        coVerify { reportApiService.getReportInfo(reservationId) }
    }

    @Test
    fun `리포트 정보 조회 실패 시 리포트 정보를 방출하지 않는다`() = runBlocking {
        // Given
        val reservationId = "2"

        coEvery { reportApiService.getReportInfo(reservationId) } returns Result.Failure(
            code = 401,
            error = "등록되지 않은 회원입니다."
        )

        // When
        val result = reportRepository.getReportFlow(reservationId).firstOrNull()

        // Then
        assertEquals(null, result)
        coVerify { reportApiService.getReportInfo(reservationId) }
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
