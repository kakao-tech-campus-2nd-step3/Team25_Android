package com.kakaotech.team25.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.di.IoDispatcher
import com.kakaotech.team25.domain.ReservationStatus.*
import com.kakaotech.team25.domain.model.AccompanyInfo
import com.kakaotech.team25.domain.model.ReservationInfo
import com.kakaotech.team25.domain.usecase.GetAccompanyInfoUseCase
import com.kakaotech.team25.domain.usecase.GetManagerNameUseCase
import com.kakaotech.team25.domain.usecase.GetReservationsUseCase
import com.kakaotech.team25.domain.usecase.LogoutUseCase
import com.kakaotech.team25.domain.usecase.WithdrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
    private val getReservationsUseCase: GetReservationsUseCase,
    private val getAccompanyInfoUseCase: GetAccompanyInfoUseCase,
    private val getManagerNameUseCase: GetManagerNameUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _withdrawEvent = MutableStateFlow<WithdrawStatus>(WithdrawStatus.Idle)
    val withdrawEvent: StateFlow<WithdrawStatus> = _withdrawEvent

    private val _confirmedReservation = MutableStateFlow<ReservationInfo?>(null)
    val confirmedReservation: StateFlow<ReservationInfo?> = _confirmedReservation

    private val _runningReservation = MutableStateFlow<ReservationInfo?>(null)
    val runningReservation: StateFlow<ReservationInfo?> = _runningReservation

    private val _accompanyInfo = MutableStateFlow<AccompanyInfo?>(null)
    val accompanyInfo: StateFlow<AccompanyInfo?> = _accompanyInfo

    private val _mangerName = MutableStateFlow<String>("")
    val managerName: StateFlow<String> = _mangerName


    init {
        updateFilteredRunningReservation()
        updateConfirmedReservation()
    }

    fun logout() {
        viewModelScope.launch(ioDispatcher) {
            logoutUseCase()
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            val withdrawResult = withdrawUseCase()
            _withdrawEvent.value = if (withdrawResult != null) {
                logout()
                WithdrawStatus.Success
            } else {
                WithdrawStatus.Failure
            }
        }
    }

    fun updateFilteredRunningReservation() {
        viewModelScope.launch {
            _runningReservation.value =
                getReservationsUseCase.invoke()?.firstOrNull { it.reservationStatus == 진행중 }
        }
    }

    fun updateConfirmedReservation() {
        viewModelScope.launch {
            _confirmedReservation.value =
                getReservationsUseCase.invoke()?.firstOrNull { it.reservationStatus == 확정 }
        }
    }

    fun updateManagerName(managerId: String) {
        viewModelScope.launch {
            _mangerName.value = getManagerNameUseCase.invoke(managerId)?: ""
        }
    }

    fun updateAccompanyInfo(reservationId: String) {
        viewModelScope.launch {
            _accompanyInfo.value =
                getAccompanyInfoUseCase.invoke(reservationId)?.lastOrNull()
        }
    }
}
