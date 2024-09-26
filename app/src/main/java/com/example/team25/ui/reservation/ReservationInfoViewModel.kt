package com.example.team25.ui.reservation

import androidx.lifecycle.ViewModel
import com.example.team25.data.Patient
import com.example.team25.data.ReservationInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReservationInfoViewModel : ViewModel() {

    private val _reservationInfo = MutableStateFlow(
        ReservationInfo(
            userId = "",
            managerId = "",
            departure = "",
            destination = "",
            serviceDate = "",
            serviceType = "",
            transportation = "",
            price = 0,
            patient = Patient(
                patientName = "",
                patientPhone = "",
                patientGender = "",
                patientRelation = "",
                patientBirth = "",
                nokPhone = ""
            )
        )
    )

    val reservationInfo: StateFlow<ReservationInfo> = _reservationInfo

    fun updateReservationInfo(reservationInfo: ReservationInfo) {
        _reservationInfo.value = reservationInfo
    }

    fun updateUserId(userId: String) {
        _reservationInfo.value = _reservationInfo.value.copy(userId = userId)
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

    fun updatePatientInfo(patient: Patient) {
        _reservationInfo.value = _reservationInfo.value.copy(patient = patient)
    }
}
