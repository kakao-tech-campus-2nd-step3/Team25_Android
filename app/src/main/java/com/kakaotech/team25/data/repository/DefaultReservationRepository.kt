package com.kakaotech.team25.data.repository

import com.kakaotech.team25.data.network.calladapter.Result.*
import com.kakaotech.team25.data.network.dto.mapper.asDomain
import android.util.Log
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
