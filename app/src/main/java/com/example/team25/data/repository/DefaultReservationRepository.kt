package com.example.team25.data.repository

import com.example.team25.data.dao.ReservationDao
import com.example.team25.data.entity.mapper.asDomain
import com.example.team25.data.entity.mapper.asEntity
import com.example.team25.data.remote.ReservationApiService
import com.example.team25.domain.model.HospitalDomain
import com.example.team25.domain.model.ReservationInfo
import com.example.team25.domain.model.ReservationStatus
import com.example.team25.domain.repository.ReservationRepository
import javax.inject.Inject

class DefaultReservationRepository @Inject constructor(
    private val reservationDao: ReservationDao,
    private val reservationApiService: ReservationApiService
) : ReservationRepository {
    override suspend fun getReservationsByStatus(status: ReservationStatus): List<ReservationInfo> {
        return reservationDao.getReservationsByStatus(status).asDomain()
    }

    override suspend fun insertReservation(reservations: List<ReservationInfo>) {
        return reservationDao.insertReservations(reservations.asEntity())
    }

    override suspend fun fetchRepository() {
        val reservations = mutableListOf<ReservationInfo>()
        val response = reservationApiService.fetchReservations()
        if (response.isSuccessful) {
            response.body()?.let { reservationDtos ->
                reservations.addAll(
                    reservationDtos.map { reservationDto ->
                        ReservationInfo(
                            managerId = reservationDto.manager.managerId,
                            managerName = reservationDto.manager.name,
                            reservationStatus = reservationDto.reservationStatus,
                            departure = reservationDto.departure,
                            destination = reservationDto.destination,
                            serviceDate = reservationDto.serviceDate,
                            serviceType = reservationDto.serviceType,
                            transportation = reservationDto.transportation,
                            price = reservationDto.price,
                            patient = reservationDto.patient
                        )
                    }
                )
            }
            reservationDao.insertReservations(reservations.asEntity())
        }
    }
}
