package com.example.team25.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.team25.domain.model.Patient
import com.example.team25.domain.ReservationStatus

@Entity("reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("manager_id") val managerId: String,
    @ColumnInfo("reservation_status") val reservationStatus: ReservationStatus,
    @ColumnInfo("departure") val departure: String,
    @ColumnInfo("destination") val destination: String,
    @ColumnInfo("serviceDate") val serviceDate: String,
    @ColumnInfo("serviceType") val serviceType: String,
    @ColumnInfo("transportation") val transportation: String,
    @ColumnInfo("price") val price: Int,
    @ColumnInfo("patient") val patient: Patient
)
