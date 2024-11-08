package com.kakaotech.team25.ui.main.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.domain.ReservationStatus
import com.kakaotech.team25.domain.ReservationStatus.보류
import com.kakaotech.team25.domain.ReservationStatus.완료
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.ReservationStatus.확정
import com.kakaotech.team25.domain.repository.ReservationRepository
import com.kakaotech.team25.domain.usecase.LoadReservationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationStatusViewModel @Inject constructor(
    private val loadReservationsUseCase: LoadReservationsUseCase,
) : ViewModel() {
    private val _reservationStatus = MutableStateFlow<List<ReservationInfo>>(emptyList())
    val reservationStatus: StateFlow<List<ReservationInfo>> = _reservationStatus

    private val _reservationHistory = MutableStateFlow<List<ReservationInfo>>(emptyList())
    val reservationHistory: StateFlow<List<ReservationInfo>> = _reservationHistory

    init {
        viewModelScope.launch {
            val reservations = loadReservationsUseCase.invoke()

            _reservationStatus.value = reservations.filter { it.reservationStatus == 보류 }
            _reservationHistory.value = reservations.filter { it.reservationStatus == 완료 }
        }
    }
}

