package com.example.team25.ui.main.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.domain.model.ReservationInfo
import com.example.team25.domain.model.ReservationStatus
import com.example.team25.domain.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationStatusViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
) : ViewModel() {
    private val _reservationStatus = MutableStateFlow<List<ReservationInfo>>(emptyList())
    val reservationStatus: StateFlow<List<ReservationInfo>> = _reservationStatus

    private val _reservationHistory = MutableStateFlow<List<ReservationInfo>>(emptyList())
    val reservationHistory: StateFlow<List<ReservationInfo>> = _reservationHistory

    init {
        fetchReservations()
    }

    fun fetchReservations() {
        viewModelScope.launch {
            reservationRepository.fetchRepository()
        }
    }

    fun getReservationsByStatus(status: ReservationStatus) {
        viewModelScope.launch {
            if (status == ReservationStatus.CONFIRM) {
                _reservationHistory.value = reservationRepository.getReservationsByStatus(status)
            } else {
                _reservationStatus.value = reservationRepository.getReservationsByStatus(status)
            }
        }
    }
}

