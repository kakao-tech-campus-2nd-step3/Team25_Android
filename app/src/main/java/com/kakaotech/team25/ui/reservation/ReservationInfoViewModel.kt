package com.kakaotech.team25.ui.reservation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.domain.Gender
import com.kakaotech.team25.domain.model.Patient
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.ReservationStatus
import com.kakaotech.team25.domain.usecase.ReserveUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationInfoViewModel @Inject constructor(
    private val reserveUseCase: ReserveUseCase,
) : ViewModel() {
    private val _reservationInfo = MutableStateFlow(
        ReservationInfo(
            managerId = "",
            managerName = "",
            reservationStatus = ReservationStatus.보류,
            departureLocation = "",
            sido = "",
            arrivalLocation = "",
            reservationDateTime = "",
            serviceType = "정기동행",
            transportation = "",
            price = 20000,
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

    private val _reserveStatus = MutableStateFlow(ReserveStatus.DEFAULT)
    val reserveStatus: StateFlow<ReserveStatus> = _reserveStatus

    fun getReservationInfo(): ReservationInfo {
        return _reservationInfo.value
    }

    fun getManagerId(): String {
        return _reservationInfo.value.managerId
    }

    fun updateReservationInfo(reservationInfo: ReservationInfo) {
        _reservationInfo.value = reservationInfo
    }

    fun updateManagerId(managerId: String) {
        _reservationInfo.value = _reservationInfo.value.copy(managerId = managerId)
    }

    fun updateManagerName(managerName: String) {
        _reservationInfo.value = _reservationInfo.value.copy(managerName = managerName)
    }

    fun updateDeparture(departure: String) {
        val sanitizedDeparture = departure.replace(Regex("\\s{2,}"), " ")
        _reservationInfo.value = _reservationInfo.value.copy(departureLocation = sanitizedDeparture)
    }

    fun updateSido(sido: String) {
        _reservationInfo.value = _reservationInfo.value.copy(sido = sido)
    }

    fun updateDestination(destination: String) {
        _reservationInfo.value = _reservationInfo.value.copy(arrivalLocation = destination)
    }

    fun updateServiceDate(year: Int, month: Int, day: Int, hour: Int, min: Int) {
        val serviceDate = String.format("%04d-%02d-%02d %02d:%02d", year, month, day, hour, min)
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

    fun reserve() {
        viewModelScope.launch {
            logReservationInfo()
            val result = reserveUseCase(_reservationInfo.value)
            _reserveStatus.value = if (result.isSuccess) {
                ReserveStatus.SUCCESS
            } else {
                ReserveStatus.FAILURE
            }
        }
    }

    fun updateReserveStatus(status: ReserveStatus) {
        _reserveStatus.value = status
    }

    fun logReservationInfo() {
        Log.d("ReservationInfoViewModel", "Current Reservation Info: ${_reservationInfo.value}")
    }
}
