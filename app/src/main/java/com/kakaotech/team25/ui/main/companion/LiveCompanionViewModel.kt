package com.kakaotech.team25.ui.main.companion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.domain.ReservationStatus.*
import com.kakaotech.team25.domain.model.AccompanyInfo
import com.kakaotech.team25.domain.repository.AccompanyRepository
import com.kakaotech.team25.domain.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveCompanionViewModel @Inject constructor(
    private val accompanyRepository: AccompanyRepository,
    private val reservationRepository: ReservationRepository
) : ViewModel() {
    private val _reservationId = MutableStateFlow<String?>(null)
    val reservationId: StateFlow<String?> = _reservationId

    private val _accompanyInfo = MutableStateFlow<List<AccompanyInfo>?>(null)
    val accompanyInfo: StateFlow<List<AccompanyInfo>?> = _accompanyInfo

    init {
        updateRunningReservationId()
    }

    fun updateRunningReservationId() {
        viewModelScope.launch {
            val runningReservationInfo = reservationRepository.getReservationsFlow().firstOrNull()
                ?.firstOrNull { it.reservationStatus == 진행중 }
            _reservationId.value = runningReservationInfo?.reservationId
        }
    }

    fun updateAccompanyInfo(reservationId: String) {
        viewModelScope.launch {
            _accompanyInfo.value = accompanyRepository.getAccompanyFlow(reservationId).firstOrNull()
        }
    }
}
