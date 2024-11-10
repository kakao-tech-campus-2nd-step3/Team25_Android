package com.kakaotech.team25.ui.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.data.network.dto.BillingKeyDto
import com.kakaotech.team25.data.network.dto.DeletePaymentRequest
import com.kakaotech.team25.data.repository.DefaultPaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationPaymentViewModel @Inject constructor(
    private val repository: DefaultPaymentRepository
) : ViewModel() {

    private val _billingKeyStatus = MutableStateFlow(BillingKeyStatus.DEFAULT)
    val billingKeyStatus: StateFlow<BillingKeyStatus> = _billingKeyStatus

    private val _paymentStatus = MutableStateFlow(PayStatus.DEFAULT)
    val paymentStatus: StateFlow<PayStatus> = _paymentStatus

    private val _expireStatus = MutableStateFlow(PayStatus.DEFAULT)
    val expireStatus: StateFlow<PayStatus> = _expireStatus

    // 결제 요청
    fun requestPayment() {
        val billingKeyDto = BillingKeyDto(
            amount = 1,
            goodsName = "테스트 상품",
            cardQuota = "0",
            useShopInterest = false
        )

        viewModelScope.launch {
            try {
                val result = repository.requestPayment(billingKeyDto)
                _paymentStatus.value = if (result.isSuccess) {
                    PayStatus.SUCCESS
                } else {
                    PayStatus.FAILURE
                }
            } catch (e: Exception) {
                _paymentStatus.value = PayStatus.FAILURE
            }
        }
    }

    // 빌링 키 만료
    fun expireBillingKey(deleteRequest: DeletePaymentRequest) {
        viewModelScope.launch {
            val result = repository.expireBillingKey(deleteRequest)

            if (result.isSuccess) {
                _billingKeyStatus.value = BillingKeyStatus.NOT_EXIST
                _expireStatus.value = PayStatus.SUCCESS
            } else {
                _expireStatus.value = PayStatus.FAILURE
            }
        }
    }

    // 카드 상태 확인
    fun checkCardStatus() {
        viewModelScope.launch {
            val result = repository.checkBillingKeyExists()
            if (result.isSuccess) {
                if (result.getOrNull()?.data?.exists == true) {
                    _billingKeyStatus.value = BillingKeyStatus.EXIST
                } else if (result.getOrNull()?.data?.exists == false){
                    _billingKeyStatus.value = BillingKeyStatus.NOT_EXIST
                } else {
                    _billingKeyStatus.value = BillingKeyStatus.FAILURE
                }
            } else {
                _billingKeyStatus.value = BillingKeyStatus.FAILURE
            }
        }
    }

    fun getBillingKeyStatus(): BillingKeyStatus {
        return _billingKeyStatus.value
    }

    fun updateBillingKeyStatus(billingKeyStatus: BillingKeyStatus) {
        _billingKeyStatus.value = billingKeyStatus
    }

    fun updateExpireStatus(payStatus: PayStatus ) {
        _expireStatus.value = payStatus
    }

    fun updatePaymentStatus(payStatus: PayStatus ) {
        _paymentStatus.value = payStatus
    }
}
