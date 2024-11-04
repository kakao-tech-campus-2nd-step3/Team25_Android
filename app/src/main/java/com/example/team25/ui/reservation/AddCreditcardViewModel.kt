package com.example.team25.ui.reservation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.data.network.dto.CreateBillingKeyRequest
import com.example.team25.data.network.dto.CreateBillingKeyResponse
import com.example.team25.data.repository.DefaultPaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class AddCreditcardViewModel @Inject constructor(
    val repository: DefaultPaymentRepository
): ViewModel(){
    private val _billingKeyResponse = MutableLiveData<CreateBillingKeyResponse?>()
    val billingKeyResponse: LiveData<CreateBillingKeyResponse?> = _billingKeyResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // 빌링 키 생성
    fun createBillingKey(createRequest: CreateBillingKeyRequest) {
        viewModelScope.launch {
            val result = repository.createBillingKey(createRequest)
            if (result.isSuccess) {
                _billingKeyResponse.value = result.getOrNull()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }
        }
    }
}
