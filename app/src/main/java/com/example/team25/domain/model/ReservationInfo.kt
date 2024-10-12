package com.example.team25.domain.model

import android.os.Parcelable
import com.example.team25.domain.ReservationStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservationInfo(
    val managerId: String,
    val managerName: String,
    val reservationStatus: ReservationStatus,
    val departure: String,
    val destination: String,
    val serviceDate: String,
    val serviceType: String,
    val transportation: String,
    val price: Int,
    val patient: Patient
) : Parcelable
