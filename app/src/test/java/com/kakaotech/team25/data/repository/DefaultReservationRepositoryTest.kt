package com.kakaotech.team25.data.repository

import android.util.Log
import com.kakaotech.team25.data.network.calladapter.Result
import com.kakaotech.team25.data.network.dto.ReservationCancelDto
import com.kakaotech.team25.data.network.dto.ReservationDto
import com.kakaotech.team25.data.network.dto.ReserveDto
import com.kakaotech.team25.data.network.dto.ServiceResponse
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import com.kakaotech.team25.data.network.response.ReserveResponse
import com.kakaotech.team25.data.remote.ReservationApiService
import com.kakaotech.team25.domain.ReservationStatus
import com.kakaotech.team25.domain.model.ReservationInfo
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
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class DefaultReservationRepositoryTest {

    private lateinit var reservationApiService: ReservationApiService
    private lateinit var reservationRepository: DefaultReservationRepository

    @Before
    fun setUp() {
        reservationApiService = mockk()
        reservationRepository = DefaultReservationRepository(reservationApiService)
        mockLogClass()
    }

    @Test
    fun `예약 전체 조회 성공 시 예약 리스트를 방출한다`() = runTest {
        // Given
        val reservationDto = listOf(
            ReservationDto(
                reservationId = "1",
                managerId = "1",
                departureLocation = "서울",
                arrivalLocation = "부산",
                reservationDate = LocalDateTime.of(2024, 11, 13, 21, 47),
                serviceType = "정기동행",
                transportation = "버스",
                reservationStatus = ReservationStatus.보류,
                price = 20000,
                patient = ReserveResponse.Patient(
                    name = "최민욱",
                    phoneNumber = "01012345678",
                    patientGender = "남성",
                    patientRelation = "본인",
                    birthDate = "1974-04-15",
                    nokPhone = "01012344321"
                )
            )
        )

        val serviceResponse = ServiceResponse(
            data = reservationDto,
            message = "예악 조회가 성공하였습니다.",
            status = true
        )

        coEvery { reservationApiService.getReservations() } returns Result.Success(
            body = serviceResponse
        )

        // When
        val result = reservationRepository.getReservationsFlow().firstOrNull()

        // Then
        assertEquals(reservationDto.asDomain(), result)
        coVerify { reservationApiService.getReservations() }
    }

    @Test
    fun `예약 전체 조회 실패 시 빈 리스트를 방출한다`() = runBlocking {
        // Given
        coEvery { reservationApiService.getReservations() } returns Result.Failure(
            code = 400,
            error = "보호자 전화번호를 반드시 입력해야합니다."
        )

        // When
        val result = reservationRepository.getReservationsFlow().firstOrNull()

        // Then
        assertEquals(emptyList<ReservationInfo>(), result)
        coVerify { reservationApiService.getReservations() }
    }

    @Test
    fun `예약 취소 성공 시 취소 성공 메세지 반환`() = runTest {
        // Given
        val reservationId = "2"
        val reservationDto = ReservationDto(
            reservationId = "2",
            managerId = "manager2",
            departureLocation = "서울",
            arrivalLocation = "부산",
            reservationDate = LocalDateTime.of(2024, 11, 13, 21, 47),
            serviceType = "정기동행",
            transportation = "버스",
            reservationStatus = ReservationStatus.보류,
            price = 20000,
            patient = ReserveResponse.Patient(
                name = "최민욱",
                phoneNumber = "01012345678",
                patientGender = "남성",
                patientRelation = "본인",
                birthDate = "1974-04-15",
                nokPhone = "01012344321"
            )
        )

        val cancelDto = ReservationCancelDto(
            cancelReason = "단순 변심",
            cancelDetail = "예약을 취소하고 싶습니다"
        )

        val serviceResponse = ServiceResponse(
            data = reservationDto,
            message = "예약 취소가 접수되었습니다",
            status = true
        )

        coEvery { reservationApiService.cancelReservation(reservationId, cancelDto) } returns Result.Success(
            body = serviceResponse
        )

        // When
        val result = reservationRepository.cancelReservation(reservationId, cancelDto)

        // Then
        assert(result.isSuccess)
        assertEquals("예약이 취소 되었습니다", result.getOrNull())
        coVerify { reservationApiService.cancelReservation(reservationId, cancelDto) }
    }

    @Test
    fun `네트워크 에러 시 실패 결과 반환`() = runTest {
        // Given
        val reservationId = "reservation123"
        val cancelDto = ReservationCancelDto(
            cancelReason = "단순 변심",
            cancelDetail = "연결이 불안정합니다"
        )

        coEvery { reservationApiService.cancelReservation(reservationId, cancelDto) } returns Result.NetworkError(
            exception = IOException("Network error")
        )

        // When
        val result = reservationRepository.cancelReservation(reservationId, cancelDto)

        // Then
        assert(result.isFailure)
        assertEquals("네트워크 연결 상태를 확인 해주세요", result.exceptionOrNull()?.message)
        coVerify { reservationApiService.cancelReservation(reservationId, cancelDto) }
    }

    @Test
    fun `예약 접수 성공 시 메세지 반환`() = runBlocking {
        // Given
        val reservationDto = ReserveDto(
            managerId = 2,
            departureLocation = "광주",
            arrivalLocation = "서울",
            reservationDateTime = "2024-09-04 15:32",
            serviceType = "정기동행",
            transportation = "버스",
            price = 20000,
            patient = ReserveDto.PatientDto(
                name = "이미옥",
                phoneNumber = "01012345678",
                patientGender = "여성",
                patientRelation = "본인",
                birthDate = "1964-02-11",
                nokPhone = "01012344321"
            )
        )

        val reserveResponse = ReserveResponse(
            status = true,
            message = "예약이 접수되었습니다",
            data = null
        )

        coEvery { reservationApiService.reserve(reservationDto) } returns Response.success(reserveResponse)

        // When
        val result = reservationRepository.reserve(reservationDto)

        // Then
        assert(result.isSuccess)
        assertEquals("예약이 접수되었습니다", result.getOrNull())
        coVerify { reservationApiService.reserve(reservationDto) }
    }

    @Test
    fun `예약 취소 실패 시 실패 결과 반환`() = runBlocking {
        // Given
        val reserveDtoEmptyDepartLocation = ReserveDto(
            managerId = 3,
            departureLocation = "",
            arrivalLocation = "서울",
            reservationDateTime = "2024-11-14 15:32",
            serviceType = "정기동행",
            transportation = "버스",
            price = 20000,
            patient = ReserveDto.PatientDto(
                name = "박경욱",
                phoneNumber = "01012345678",
                patientGender = "남성",
                patientRelation = "본인",
                birthDate = "1958-12-11",
                nokPhone = "01012344321"
            )
        )

        val reserveDtoEmptyArrivalLocation = ReserveDto(
            managerId = 4,
            departureLocation = "부산",
            arrivalLocation = "서울",
            reservationDateTime = "2024-11-14 15:32",
            serviceType = "정기동행",
            transportation = "버스",
            price = 20000,
            patient = ReserveDto.PatientDto(
                name = "최미자",
                phoneNumber = "01012345678",
                patientGender = "여성",
                patientRelation = "본인",
                birthDate = "1968-12-11",
                nokPhone = "01012344321"
            )
        )

        coEvery { reservationApiService.reserve(reserveDtoEmptyDepartLocation) } returns Response.error(
            400,
            "유효하지 않은 출발 장소 입니다".toResponseBody()
        )

        coEvery { reservationApiService.reserve(reserveDtoEmptyArrivalLocation) } returns Response.error(
            400,
            "유효하지 않은 도착 장소 입니다".toResponseBody()
        )

        // When
        val resultInvalidDepartureAddress = reservationRepository.reserve(reserveDtoEmptyDepartLocation)
        val resultInvalidArrivalAddress = reservationRepository.reserve(reserveDtoEmptyArrivalLocation)


        // Then
        assert(resultInvalidDepartureAddress.isFailure)
        assert(resultInvalidArrivalAddress.isFailure)

        coVerify { reservationApiService.reserve(reserveDtoEmptyDepartLocation) }
        coVerify { reservationApiService.reserve(reserveDtoEmptyArrivalLocation) }
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
