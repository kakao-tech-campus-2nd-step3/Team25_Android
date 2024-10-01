package com.example.team25.ui.reservation

import androidx.lifecycle.ViewModel
import com.example.team25.domain.Gender
import com.example.team25.domain.model.Patient
import com.example.team25.domain.model.ReservationInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReservationInfoViewModel @Inject constructor() : ViewModel() {

    private val _reservationInfo = MutableStateFlow(
        ReservationInfo(
            managerId = "",
            departure = "",
            destination = "",
            serviceDate = "",
            serviceType = "외래진료",
            transportation = "",
            price = 0,
            patient = Patient(
                patientName = "",
                patientPhone = "",
                patientGender = Gender.MALE,
                patientRelation = "본인",
                patientBirth = "",
                nokPhone = ""
            )
        )
    )

    val reservationInfo: StateFlow<ReservationInfo> = _reservationInfo

    fun updateReservationInfo(reservationInfo: ReservationInfo) {
        _reservationInfo.value = reservationInfo
    }

    fun updateManagerId(managerId: String) {
        _reservationInfo.value = _reservationInfo.value.copy(managerId = managerId)
    }

    fun updateDeparture(departure: String) {
        _reservationInfo.value = _reservationInfo.value.copy(departure = departure)
    }

    fun updateDestination(destination: String) {
        _reservationInfo.value = _reservationInfo.value.copy(destination = destination)
    }

    fun updateServiceDate(serviceDate: String) {
        _reservationInfo.value = _reservationInfo.value.copy(serviceDate = serviceDate)
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
