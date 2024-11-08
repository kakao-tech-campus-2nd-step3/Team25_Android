package com.kakaotech.team25.data.repository

import android.util.Log
import com.kakaotech.team25.data.dao.ReservationDao
import com.kakaotech.team25.data.entity.mapper.asDomainFromDto
import com.kakaotech.team25.data.entity.mapper.asDomainFromEntity
import com.kakaotech.team25.data.entity.mapper.asEntity
import com.kakaotech.team25.data.network.calladapter.Result.*
import com.kakaotech.team25.data.network.dto.ReserveDto
import com.kakaotech.team25.data.remote.ReservationApiService
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.ReservationStatus
import com.kakaotech.team25.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultReservationRepository @Inject constructor(
    private val reservationDao: ReservationDao,
    private val reservationApiService: ReservationApiService
) : ReservationRepository {
    override val reservationsFlow: Flow<List<ReservationInfo>> = getAllReservationsFlow()
    private fun getAllReservationsFlow(): Flow<List<ReservationInfo>> {
        return reservationDao.getAllReservationsFlow().map { it.asDomainFromEntity()}
    }

    override suspend fun getReservationsByStatus(status: ReservationStatus): List<ReservationInfo> {
        return reservationDao.getReservationsByStatus(status).asDomainFromEntity()
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

    override suspend fun insertReservation(reservations: List<ReservationInfo>) {
        return reservationDao.insertReservations(reservations.asEntity())
    }

    override suspend fun fetchReservations() {
        val result = reservationApiService.fetchReservations()
        if (result is Success) result.body?.data?.let { reservationDtos ->
            insertReservation(reservationDtos.asDomainFromDto())
        }
    }

    companion object {
        private const val TAG = "reservationRepository"
    }
}
