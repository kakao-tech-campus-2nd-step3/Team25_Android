package com.example.team25.domain.model

import android.os.Parcelable
import com.example.team25.domain.ReservationStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservationInfo(
    val reservationId: String = "",
    val managerId: String = "",
    val managerName: String = "",
    val reservationStatus: ReservationStatus = ReservationStatus.보류,
    val departureLocation: String = "",
    val arrivalLocation: String = "",
    val reservationDate: String = "",
    val serviceType: String = "",
    val transportation: String = "",
    val price: Int = 0,
    val patient: Patient = Patient()
) : Parcelable
