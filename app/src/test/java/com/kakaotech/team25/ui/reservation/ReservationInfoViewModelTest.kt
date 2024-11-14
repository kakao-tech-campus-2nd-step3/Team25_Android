package com.kakaotech.team25.ui.reservation

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kakaotech.team25.domain.Gender
import com.kakaotech.team25.domain.model.Patient
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.ReservationStatus
import com.kakaotech.team25.domain.usecase.ReserveUseCase
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ReservationInfoViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var reservationInfoViewModel: ReservationInfoViewModel
    private val reserveUseCase: ReserveUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        reservationInfoViewModel = ReservationInfoViewModel(reserveUseCase)
        mockLogClass()
    }

    @Test
    fun `예약 성공 테스트`() = runTest {
        // Given
        val reservationInfo = ReservationInfo(
            managerId = "managerId",
            managerName = "managerName",
            reservationStatus = ReservationStatus.보류,
            departureLocation = "서울",
            sido = "서울",
            arrivalLocation = "부산",
            reservationDateTime = "2024-11-12 10:00",
            serviceType = "정기동행",
            transportation = "버스",
            price = 20000,
            patient = Patient(
                patientName = "홍길동",
                patientPhone = "010-1234-5678",
                patientGender = Gender.MALE,
                patientRelation = "본인",
                patientBirth = "1980-01-01",
                nokPhone = "010-8765-4321"
            )
        )

        // When
        coEvery { reserveUseCase(any()) } returns Result.success("예약 성공")

        reservationInfoViewModel.updateReservationInfo(reservationInfo)
        reservationInfoViewModel.reserve()

        advanceUntilIdle()

        // Then
        assert(reservationInfoViewModel.reserveStatus.value == ReserveStatus.SUCCESS)
        coVerify { reserveUseCase(any()) }
    }

    @Test
    fun `예약 실패 결과 처리 테스트`() = runTest {
        // Given
        val reservationInfo = ReservationInfo(
            managerId = "managerId",
            managerName = "managerName",
            reservationStatus = ReservationStatus.보류,
            departureLocation = "서울",
            sido = "서울",
            arrivalLocation = "부산",
            reservationDateTime = "2024-11-12 10:00",
            serviceType = "정기동행",
            transportation = "버스",
            price = 20000,
            patient = Patient(
                patientName = "홍길동",
                patientPhone = "010-1234-5678",
                patientGender = Gender.MALE,
                patientRelation = "본인",
                patientBirth = "1980-01-01",
                nokPhone = "010-8765-4321"
            )
        )

        // When
        coEvery { reserveUseCase(any()) } returns Result.failure(Exception("예약 실패"))

        reservationInfoViewModel.updateReservationInfo(reservationInfo)
        reservationInfoViewModel.reserve()

        advanceUntilIdle()

        // Then
        assert(reservationInfoViewModel.reserveStatus.value == ReserveStatus.FAILURE)
        coVerify { reserveUseCase(any()) }
    }

    private fun mockLogClass() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
