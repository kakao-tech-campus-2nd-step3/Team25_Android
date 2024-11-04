package com.example.team25.ui.main.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.domain.model.Report
import com.example.team25.domain.model.ReservationInfo
import com.example.team25.domain.repository.ReportRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReservationCheckReportViewModel(
    private val reportRepository: ReportRepository
) : ViewModel() {
    private val _reservationInfo = MutableStateFlow(ReservationInfo())
    val reservationInfo: StateFlow<ReservationInfo> = _reservationInfo

    @OptIn(ExperimentalCoroutinesApi::class)
    val reportInfo: StateFlow<Report> = _reservationInfo
        .flatMapLatest { reservationInfo ->
            reportRepository.getReportFlow(reservationInfo.reservationId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = Report()
        )

    fun updateReservationInfo(reservationInfo: ReservationInfo){
        viewModelScope.launch {
            _reservationInfo.value = reservationInfo
        }
    }
}
