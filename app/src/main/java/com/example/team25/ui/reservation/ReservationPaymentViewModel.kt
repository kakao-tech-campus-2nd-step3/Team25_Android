package com.example.team25.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.data.network.dto.BillingKeyDto
import com.example.team25.data.network.dto.BillingKeyExistsResponse
import com.example.team25.data.network.dto.CreateBillingKeyRequest
import com.example.team25.data.network.dto.CreateBillingKeyResponse
import com.example.team25.data.network.dto.DeletePaymentRequest
import com.example.team25.data.network.dto.DeletePaymentResponse
import com.example.team25.data.network.dto.PaymentResponse
import com.example.team25.data.repository.DefaultPaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationPaymentViewModel @Inject constructor(
    private val repository: DefaultPaymentRepository
) : ViewModel() {
    private val _paymentResponse = MutableLiveData<PaymentResponse?>()
    val paymentResponse: LiveData<PaymentResponse?> = _paymentResponse

    private val _billingKeyExpirationResponse = MutableLiveData<DeletePaymentResponse?>()
    val billingKeyExpirationResponse: LiveData<DeletePaymentResponse?> = _billingKeyExpirationResponse

    private val _billingKeyExistsResponse = MutableLiveData<BillingKeyExistsResponse?>()
    val billingKeyExistsResponse: LiveData<BillingKeyExistsResponse?> = _billingKeyExistsResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    // 결제 요청
    fun requestPayment(payRequest: BillingKeyDto) {
        viewModelScope.launch {
            val result = repository.requestPayment(payRequest)
            if (result.isSuccess) {
                _paymentResponse.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }



    // 빌링 키 만료
    fun expireBillingKey(deleteRequest: DeletePaymentRequest) {
        viewModelScope.launch {
            val result = repository.expireBillingKey(deleteRequest)
            if (result.isSuccess) {
                _billingKeyExpirationResponse.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }

    // 빌링 키 존재 여부 확인
    fun checkBillingKeyExists() {
        viewModelScope.launch {
            val result = repository.checkBillingKeyExists()
            if (result.isSuccess) {
                _billingKeyExistsResponse.value = result.getOrNull()

            } else {
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }
}
