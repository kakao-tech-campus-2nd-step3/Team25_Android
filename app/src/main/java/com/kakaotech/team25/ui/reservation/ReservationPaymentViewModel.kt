package com.kakaotech.team25.ui.reservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.data.network.dto.BillingKeyDto
import com.kakaotech.team25.data.network.dto.DeletePaymentRequest
import com.kakaotech.team25.data.repository.DefaultPaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationPaymentViewModel @Inject constructor(
    private val repository: DefaultPaymentRepository
) : ViewModel() {
    private val _billingKeyStatusMessage = MutableLiveData<String>()
    val billingKeyStatusMessage: LiveData<String> = _billingKeyStatusMessage

    private val _paymentStatusMessage = MutableLiveData<String>()
    val paymentStatusMessage: LiveData<String> = _paymentStatusMessage

    private val _cardStatusText = MutableLiveData<String>()
    val cardStatusText: LiveData<String> = _cardStatusText

    // 결제 요청
    private fun requestPayment(payRequest: BillingKeyDto) {
        viewModelScope.launch {
            try {
                val result = repository.requestPayment(payRequest)
                _paymentStatusMessage.value = if (result.isSuccess) {
                    "Payment successful: ${result.getOrNull()?.message}"
                } else {
                    "Payment failed: ${result.exceptionOrNull()?.message}"
                }
            } catch (e: Exception) {
                _paymentStatusMessage.value = "Payment request error: ${e.localizedMessage}"
                Log.e("ReservationPaymentViewModel", "Error in requestPayment: ", e)
            }
        }
    }

    // 빌링 키 만료
    fun expireBillingKey(deleteRequest: DeletePaymentRequest) {
        viewModelScope.launch {
            val result = repository.expireBillingKey(deleteRequest)
            _billingKeyStatusMessage.value = if (result.isSuccess) {
                "Billing key deletion successful"
            } else {
                "Billing key deletion failed: ${result.exceptionOrNull()?.message}"
            }
        }
    }

    // 카드 상태 확인
    fun checkCardStatus() {
        viewModelScope.launch {
            val result = repository.checkBillingKeyExists()
            _cardStatusText.value = if (result.isSuccess && result.getOrNull()?.data?.exists == true) {
                "${result.getOrNull()?.data?.cardName} (선택시 삭제)"
            } else {
                "카드가 존재하지 않습니다."
            }
        }
    }

    // 결제 시작 전 빌링 키 확인
    fun initiatePaymentCheck() {
        viewModelScope.launch {
            val result = repository.checkBillingKeyExists()
            if (result.isSuccess && result.getOrNull()?.data?.exists == true) {
                requestPayment(BillingKeyDto(
                    amount = 1,
                    goodsName = "테스트 상품",
                    cardQuota = "0",
                    useShopInterest = false,
                    reservationId = 1
                ))
            } else {
                _billingKeyStatusMessage.value = "Billing key does not exist, navigate to add credit card."

            }
        }
    }
}
