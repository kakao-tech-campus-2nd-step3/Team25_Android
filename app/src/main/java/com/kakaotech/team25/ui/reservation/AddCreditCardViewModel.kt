package com.kakaotech.team25.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25.data.network.dto.CreateBillingKeyRequest
import com.kakaotech.team25.data.network.dto.CreateBillingKeyResponse
import com.kakaotech.team25.data.repository.DefaultPaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCreditCardViewModel @Inject constructor(
    private val repository: DefaultPaymentRepository
) : ViewModel() {

    private val _billingKeyResponse = MutableLiveData<CreateBillingKeyResponse?>()
    val billingKeyResponse: LiveData<CreateBillingKeyResponse?> = _billingKeyResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun createBillingKey(createRequest: CreateBillingKeyRequest) {
        viewModelScope.launch {
            val result = repository.createBillingKey(createRequest)

            if (result.isSuccess) {
                _billingKeyResponse.value = result.getOrNull()
            } else {
                val exception = result.exceptionOrNull()
                _error.value = exception?.message
            }
        }
    }
    fun resetBillingKeyResponse() {
        _billingKeyResponse.value = null
    }
}

