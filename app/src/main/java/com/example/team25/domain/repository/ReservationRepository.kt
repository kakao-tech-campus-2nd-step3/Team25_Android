package com.example.team25.domain.repository

import com.example.team25.data.entity.ReservationEntity
import com.example.team25.domain.model.ReservationInfo
import com.example.team25.domain.ReservationStatus

interface ReservationRepository {
    suspend fun fetchRepository()

    suspend fun insertReservation(reservations :List<ReservationInfo>)

    suspend fun getAllReservations(): List<ReservationInfo>

    suspend fun getReservationsByStatus(status: ReservationStatus): List<ReservationInfo>
}
