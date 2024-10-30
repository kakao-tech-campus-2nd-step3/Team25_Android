package com.example.team25.data.repository

import com.example.team25.data.network.dto.*
import com.example.team25.data.network.remote.PaymentApiService
import javax.inject.Inject

class DefaultPaymentRepository @Inject constructor(
    private val paymentService: PaymentApiService
) {
    suspend fun requestPayment(payRequest: BillingKeyDto): Result<PaymentResponse> {
        return try {
            val response = paymentService.requestPay(payRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Payment request failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createBillingKey(createRequest: CreateBillingKeyRequest): Result<CreateBillingKeyResponse> {
        return try {
            val response = paymentService.createBillingKey(createRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Result.failure(Exception("Payment failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun expireBillingKey(deleteRequest: DeletePaymentRequest): Result<DeletePaymentResponse> {
        return try {
            val response = paymentService.deleteBillingKey(deleteRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Result.failure(Exception("Payment failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun checkBillingKeyExists(): Result<BillingKeyExistsResponse>{
        return try {
            val response = paymentService.checkBillingKeyExists()
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Result.failure(Exception("Payment failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}
