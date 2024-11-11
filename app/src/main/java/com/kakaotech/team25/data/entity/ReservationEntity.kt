package com.kakaotech.team25.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kakaotech.team25.domain.ReservationStatus

@Entity("reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("manager_id") val managerId: String,
    @ColumnInfo("reservation_id") val reservationId: String,
    @ColumnInfo("reservation_status") val reservationStatus: ReservationStatus,
    @ColumnInfo("departure_location") val departureLocation: String,
    @ColumnInfo("arrival_location") val arrivalLocation: String,
    @ColumnInfo("reservation_date") val reservationDate: String,
    @ColumnInfo("service_type") val serviceType: String,
    @ColumnInfo("transportation") val transportation: String,
    @ColumnInfo("price") val price: Int,
)
