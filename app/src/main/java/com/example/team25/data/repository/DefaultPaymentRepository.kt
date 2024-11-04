package com.example.team25.data.repository

import android.util.Log
import com.example.team25.data.network.dto.*
import com.example.team25.data.network.remote.PaymentApiService
import javax.inject.Inject

class DefaultPaymentRepository @Inject constructor(
    private val paymentService: PaymentApiService
){
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
            Log.d("Repository", "checkBillingKeyExists API 호출 성공: ${response.isSuccessful}")
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("Repository", "checkBillingKeyExists 응답: $responseBody")
                    Result.success(responseBody)
                } else {
                    Log.e("Repository", "Empty response body")
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("Repository", "Payment request failed: $errorBody")
                Result.failure(Exception("Payment failed: $errorBody"))
            }
        } catch (e: Exception) {
            Log.e("Repository", "Exception during API call", e)
            Result.failure(e)
        }

    }
}
