package com.kakaotech.team25.ui.reservation

import androidx.lifecycle.ViewModel
import com.kakaotech.team25.domain.Gender
import com.kakaotech.team25.domain.model.Patient
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.ReservationStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReservationInfoViewModel @Inject constructor() : ViewModel() {
    private val _reservationInfo = MutableStateFlow(
        ReservationInfo(
            managerId = "",
            managerName = "",
            reservationStatus = ReservationStatus.보류,
            departureLocation = "",
            sido = "",
            arrivalLocation = "",
            reservationDateTime = "",
            serviceType = "외래진료",
            transportation = "",
            price = 0,
            patient = Patient(
                patientName = "",
                patientPhone = "",
                patientGender = Gender.MALE,
                patientRelation = "본인",
                patientBirth = "",
                nokPhone = "",

                ),
        ),
    )

    val reservationInfo: StateFlow<ReservationInfo> = _reservationInfo

    fun updateReservationInfo(reservationInfo: ReservationInfo) {
        _reservationInfo.value = reservationInfo
    }

    fun updateManagerId(managerId: String) {
        _reservationInfo.value = _reservationInfo.value.copy(managerId = managerId)
    }

    fun updateDeparture(departure: String) {
        _reservationInfo.value = _reservationInfo.value.copy(departureLocation = departure)
    }

    fun updateSido(sido: String) {
        _reservationInfo.value = _reservationInfo.value.copy(sido = sido)
    }

    fun updateDestination(destination: String) {
        _reservationInfo.value = _reservationInfo.value.copy(arrivalLocation = destination)
    }

    fun updateServiceDate(year: Int, month: Int, day: Int, hour: Int, min: Int) {
        val serviceDate = String.format(
            "%04d-%02d-%02d %02d:%02d",
            year, month, day, hour, min
        )
        _reservationInfo.value = _reservationInfo.value.copy(reservationDateTime = serviceDate)
    }

    fun updateServiceType(serviceType: String) {
        _reservationInfo.value = _reservationInfo.value.copy(serviceType = serviceType)
    }

    fun updateTransportation(transportation: String) {
        _reservationInfo.value = _reservationInfo.value.copy(transportation = transportation)
    }

    fun updatePrice(price: Int) {
        _reservationInfo.value = _reservationInfo.value.copy(price = price)
    }

    private fun updatePatientInfo(patient: Patient) {
        _reservationInfo.value = _reservationInfo.value.copy(patient = patient)
    }

    fun updatePatientName(name: String) {
        val updatedPatient = _reservationInfo.value.patient.copy(patientName = name)
        updatePatientInfo(updatedPatient)
    }

    fun updatePatientPhone(phone: String) {
        val updatedPatient = _reservationInfo.value.patient.copy(patientPhone = phone)
        updatePatientInfo(updatedPatient)
    }

    fun updatePatientGender(gender: Gender) {
        val updatedPatient = _reservationInfo.value.patient.copy(patientGender = gender)
        updatePatientInfo(updatedPatient)
    }

    fun updatePatientRelation(relation: String) {
        val updatedPatient = _reservationInfo.value.patient.copy(patientRelation = relation)
        updatePatientInfo(updatedPatient)
    }

    fun updatePatientBirth(birth: String) {
        val updatedPatient = _reservationInfo.value.patient.copy(patientBirth = birth)
        updatePatientInfo(updatedPatient)
    }

    fun updateNokPhone(nokPhone: String) {
        val updatedPatient = _reservationInfo.value.patient.copy(nokPhone = nokPhone)
        updatePatientInfo(updatedPatient)
    }
}
