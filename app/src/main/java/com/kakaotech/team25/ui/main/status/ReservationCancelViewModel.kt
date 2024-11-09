package com.kakaotech.team25.ui.main.status

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.data.network.dto.ReservationCancelDto
import com.kakaotech.team25.domain.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationCancelViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository
) : ViewModel() {
    private val _reservationId = MutableStateFlow("")
    val reservationId: StateFlow<String> = _reservationId

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    private val _reservationCancelDto = MutableStateFlow(
        ReservationCancelDto(
            cancelReason = "",
            cancelDetail = ""
        )
    )
    val reservationCancelDto: StateFlow<ReservationCancelDto> = _reservationCancelDto

    fun updateReservationId(reservationId: String){
        _reservationId.value = reservationId
    }

    fun updateCancelReason(cancelReason: String){
        _reservationCancelDto.value = _reservationCancelDto.value.copy(cancelReason = cancelReason)
    }

    fun updateCancelDetails(cancelDetails: String){
        _reservationCancelDto.value = _reservationCancelDto.value.copy(cancelDetail = cancelDetails)
    }

    fun cancelReservation() {
        viewModelScope.launch {
            val reservationId = reservationId.value
            val reservationCancelDto = reservationCancelDto.value

            val result = reservationRepository.cancelReservation(reservationId, reservationCancelDto)
            result.fold(
                onSuccess = { message ->
                    _toastMessage.value = message
                },
                onFailure = { throwable ->
                    _toastMessage.value = throwable.message
                }
            )
        }
    }
}
