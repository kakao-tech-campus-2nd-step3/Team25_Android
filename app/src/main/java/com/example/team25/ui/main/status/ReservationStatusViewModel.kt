package com.example.team25.ui.main.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.domain.model.ReservationInfo
import com.example.team25.domain.ReservationStatus
import com.example.team25.domain.ReservationStatus.CONFIRM
import com.example.team25.domain.repository.ReservationRepository
import com.example.team25.domain.usecase.FetchRepositoriesUseCase
import com.example.team25.domain.usecase.LoadReservationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationStatusViewModel @Inject constructor(
    private val fetchRepositoriesUseCase: FetchRepositoriesUseCase,
    private val loadReservationsUseCase: LoadReservationsUseCase,
) : ViewModel() {
    private val _reservationStatus = MutableStateFlow<List<ReservationInfo>>(emptyList())
    val reservationStatus: StateFlow<List<ReservationInfo>> = _reservationStatus

    private val _reservationHistory = MutableStateFlow<List<ReservationInfo>>(emptyList())
    val reservationHistory: StateFlow<List<ReservationInfo>> = _reservationHistory

    init {
        fetchRepositories()
        loadReservations()
    }

    private fun fetchRepositories() {
        viewModelScope.launch {
            fetchRepositoriesUseCase.invoke()
        }
    }

    private fun loadReservations() {
        viewModelScope.launch {
            val reservations = loadReservationsUseCase.invoke()

            _reservationStatus.value = reservations.filter { it.reservationStatus == CONFIRM }
            _reservationHistory.value = reservations.filter { it.reservationStatus != CONFIRM }
        }
    }
}

