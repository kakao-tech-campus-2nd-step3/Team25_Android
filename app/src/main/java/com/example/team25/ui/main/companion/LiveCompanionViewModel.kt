package com.example.team25.ui.main.companion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.domain.ReservationStatus
import com.example.team25.domain.model.AccompanyInfo
import com.example.team25.domain.model.ReservationInfo
import com.example.team25.domain.repository.AccompanyRepository
import com.example.team25.domain.repository.ReservationRepository
import com.kakao.vectormap.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveCompanionViewModel @Inject constructor(
    private val accompanyRepository: AccompanyRepository,
    private val reservationRepository: ReservationRepository
) : ViewModel() {
    private val _reservationInfo = MutableStateFlow(ReservationInfo())
    val reservationInfo: StateFlow<ReservationInfo> = _reservationInfo

    @OptIn(ExperimentalCoroutinesApi::class)
    val accompanyInfoList: StateFlow<List<AccompanyInfo>> = _reservationInfo
        .flatMapLatest { reservationInfo ->
            accompanyRepository.getAccompanyFlow(reservationInfo.reservationId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val coordinateInfo: StateFlow<LatLng> = _reservationInfo
        .flatMapLatest { reservationInfo ->
            accompanyRepository.getCoordinatesFlow(reservationInfo.reservationId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LatLng.from(35.55, 128.0)
        )

    init {
        fetchReservations()
        updateReservationsByStatus()
    }

    private fun fetchReservations() {
        viewModelScope.launch {
            reservationRepository.fetchReservations()
        }
    }

    private fun updateReservationsByStatus() {
        viewModelScope.launch {
            val reservations = reservationRepository.getReservationsByStatus(ReservationStatus.RUNNING)
            if (reservations.isNotEmpty()) {
                _reservationInfo.value = reservations.last()
            } else {
                _reservationInfo.value = ReservationInfo()
            }
        }
    }
}
