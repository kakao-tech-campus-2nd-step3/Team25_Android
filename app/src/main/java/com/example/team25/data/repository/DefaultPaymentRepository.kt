package com.example.team25.data.repository

import com.example.team25.data.network.dto.*
import com.example.team25.data.network.services.PaymentService
import javax.inject.Inject

class DefaultPaymentRepository @Inject constructor(
    private val paymentService: PaymentService
) {
    suspend fun requestPayment(payRequest: BillingKeyDto): PaymentResponse {
        val response = paymentService.requestPay(payRequest)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }

    suspend fun createBillingKey(createRequest: CreateBillingKeyRequest): CreateBillingKeyResponse {
        val response = paymentService.createBillingKey(createRequest)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }

    suspend fun expireBillingKey(deleteRequest: DeletePaymentRequest): DeletePaymentResponse {
        val response = paymentService.deleteBillingKey(deleteRequest)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }

    suspend fun checkBillingKeyExists(): BillingKeyExistsResponse {
        val response = paymentService.checkBillingKeyExists()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw Exception()
        }
    }
}
