package com.kakaotech.team25.domain.model

import android.os.Parcelable
import com.kakaotech.team25.data.network.dto.ReserveDto
import com.kakaotech.team25.domain.Gender
import com.kakaotech.team25.domain.ReservationStatus
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservationInfo(
    val reservationId: String = "",
    val managerId: String = "",
    var managerName: String = "",
    val reservationStatus: ReservationStatus = ReservationStatus.보류,
    val departureLocation: String = "",
    val sido : String = "",
    val arrivalLocation: String = "",
    val reservationDateTime: String? = "",
    val serviceType: String = "",
    val transportation: String = "",
    val price: Int = 0,
    val patient: Patient = Patient()
) : Parcelable

fun ReservationInfo.toReserveDto(): ReserveDto {
    return ReserveDto(
        managerId = this.managerId.toIntOrNull() ?: 0,
        departureLocation = this.departureLocation,
        arrivalLocation = this.arrivalLocation,
        reservationDateTime = this.reservationDateTime,
        serviceType = this.serviceType,
        transportation = this.transportation,
        price = this.price,
        patient = this.patient.toPatientDto()
    )
}

fun Patient.toPatientDto(): ReserveDto.PatientDto {
    return ReserveDto.PatientDto(
        name = this.patientName,
        phoneNumber = this.patientPhone,
        patientGender = when (this.patientGender) {
            Gender.MALE -> "남성"
            Gender.FEMALE -> "여성"
            Gender.UNKNOWN -> "알 수 없음"
        },
        patientRelation = this.patientRelation,
        birthDate = this.patientBirth,
        nokPhone = this.nokPhone
    )
}
