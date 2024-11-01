package com.example.team25.data.repository

import com.example.team25.data.dao.ReservationDao
import com.example.team25.data.entity.mapper.asDomainFromDto
import com.example.team25.data.entity.mapper.asDomainFromEntity
import com.example.team25.data.entity.mapper.asEntity
import com.example.team25.data.network.calladapter.Result.*
import com.example.team25.data.remote.ReservationApiService
import com.example.team25.domain.model.ReservationInfo
import com.example.team25.domain.ReservationStatus
import com.example.team25.domain.repository.ReservationRepository
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

    override suspend fun insertReservation(reservations: List<ReservationInfo>) {
        return reservationDao.insertReservations(reservations.asEntity())
    }

    override suspend fun fetchReservations() {
        val result = reservationApiService.fetchReservations()
        if (result is Success) result.body?.data?.let { reservationDtos ->
            insertReservation(reservationDtos.asDomainFromDto())
        }
    }
}
