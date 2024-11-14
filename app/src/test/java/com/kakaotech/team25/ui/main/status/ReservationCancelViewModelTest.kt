package com.kakaotech.team25.ui.main.status

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kakaotech.team25.data.network.dto.ReservationCancelDto
import com.kakaotech.team25.domain.usecase.CancelReservationUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ReservationCancelViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ReservationCancelViewModel
    private val cancelReservationUseCase: CancelReservationUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ReservationCancelViewModel(cancelReservationUseCase)
    }

    @Test
    fun `예약 취소 성공 테스트`() = runTest {
        // Given
        val reservationId = "reservation123"
        val reservationCancelDto = ReservationCancelDto(
            cancelReason = "단순변심",
            cancelDetail = "단순 변심으로 인한 예약 취소"
        )
        viewModel.updateReservationId(reservationId)
        viewModel.updateCancelReason(reservationCancelDto.cancelReason)
        viewModel.updateCancelDetails(reservationCancelDto.cancelDetail)

        // 예약 취소 성공 시 메시지
        val successMessage = "예약이 성공적으로 취소되었습니다"
        coEvery { cancelReservationUseCase(reservationId, reservationCancelDto) } returns Result.success(successMessage)

        // When
        viewModel.cancelReservation()
        advanceUntilIdle() // 테스트 디스패처에 의해 완료될 때까지 기다림

        // Then
        assertEquals(successMessage, viewModel.toastMessage.value)
        coVerify { cancelReservationUseCase(reservationId, reservationCancelDto) }
    }

    @Test
    fun `예약 취소 실패 테스트`() = runTest {
        // Given
        val reservationId = "reservation123"
        val reservationCancelDto = ReservationCancelDto(
            cancelReason = "변경 사유",
            cancelDetail = "세부 사유"
        )
        viewModel.updateReservationId(reservationId)
        viewModel.updateCancelReason(reservationCancelDto.cancelReason)
        viewModel.updateCancelDetails(reservationCancelDto.cancelDetail)

        // 예약 취소 실패 시 예외 메시지
        val errorMessage = "예약 취소에 실패했습니다"
        coEvery { cancelReservationUseCase(reservationId, reservationCancelDto) } returns Result.failure(Exception(errorMessage))

        // When
        viewModel.cancelReservation()
        advanceUntilIdle()

        // Then
        assertEquals(errorMessage, viewModel.toastMessage.value)
        coVerify { cancelReservationUseCase(reservationId, reservationCancelDto) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
