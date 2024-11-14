package com.kakaotech.team25.ui.main.status

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kakaotech.team25.domain.ReservationStatus
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.usecase.LoadReservationsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ReservationStatusViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ReservationStatusViewModel
    private val loadReservationsUseCase: LoadReservationsUseCase = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ReservationStatusViewModel(loadReservationsUseCase)
    }

    @Test
    fun `updateReservations 함수는 보류와 완료 상태의 예약을 필터링한다`() = runTest {
        // Given
        val reservations = listOf(
            ReservationInfo(
                managerId = "manager1",
                reservationStatus = ReservationStatus.보류,
                managerName = "Manager 1"
            ),
            ReservationInfo(
                managerId = "manager2",
                reservationStatus = ReservationStatus.완료,
                managerName = "Manager 2"
            ),
            ReservationInfo(
                managerId = "manager3",
                reservationStatus = ReservationStatus.확정,
                managerName = "Manager 3"
            )
        )
        coEvery { loadReservationsUseCase.invoke() } returns reservations

        // When
        viewModel.updateReservations()
        advanceUntilIdle()

        // Then
        val reservationStatus = viewModel.reservationStatus.first()
        val reservationHistory = viewModel.reservationHistory.first()

        assertEquals(1, reservationStatus.size)
        assertEquals(ReservationStatus.보류, reservationStatus[0].reservationStatus)

        assertEquals(1, reservationHistory.size)
        assertEquals(ReservationStatus.완료, reservationHistory[0].reservationStatus)

        coVerify { loadReservationsUseCase.invoke() }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
