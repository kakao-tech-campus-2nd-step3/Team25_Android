package com.kakaotech.team25.data.repository

import com.kakaotech.team25.data.network.calladapter.Result.*
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import android.util.Log
import com.kakaotech.team25.data.network.dto.ReservationCancelDto
import com.kakaotech.team25.data.network.dto.ReserveDto
import com.kakaotech.team25.data.remote.ReservationApiService
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultReservationRepository @Inject constructor(
    private val reservationApiService: ReservationApiService
) : ReservationRepository {
    override fun getReservationsFlow(): Flow<List<ReservationInfo>> = flow{
        val result = reservationApiService.getReservations()
        if (result is Success) result.body?.data?.let { reservationDto ->
            emit(reservationDto.asDomain())
        }
    }

    override suspend fun cancelReservation(reservationId: String, reservationCancelDto: ReservationCancelDto): Result<String> {
        val result = reservationApiService.cancelReservation(reservationId, reservationCancelDto)
        return when(result){
            is Success -> Result.success("예약이 취소 되었습니다")
            is Failure -> Result.failure(Exception("오류가 발생했습니다. 다시 시도해주세요"))
            is NetworkError -> Result.failure(Exception("네트워크 연결 상태를 확인 해주세요"))
            else -> Result.failure(Exception("알 수 없는 오류가 발생했습니다. 다시 시도해주세요"))
        }
    }

    override suspend fun reserve(reserveDto: ReserveDto): Result<String?> {
        return try {
            val response = reservationApiService.reserve(reserveDto)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status == true) {
                    Result.success(responseBody.message)
                } else {
                    Log.e(TAG, "Invalid response body or status is false")
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Log.e(TAG, "Resevation failed with status code: ${response.code()}")
                Result.failure(Exception("Registration failed"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception occurred: ${e.message}", e)
            Result.failure(e)
        }
    }

    companion object {
        private const val TAG = "reservationRepository"
    }
}
