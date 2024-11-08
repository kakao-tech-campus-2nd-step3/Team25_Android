package com.kakaotech.team25.data.repository

import android.util.Log
import com.kakaotech.team25.data.network.dto.BillingKeyDto
import com.kakaotech.team25.data.network.dto.BillingKeyExistsResponse
import com.kakaotech.team25.data.network.dto.CreateBillingKeyRequest
import com.kakaotech.team25.data.network.dto.CreateBillingKeyResponse
import com.kakaotech.team25.data.network.dto.DeletePaymentRequest
import com.kakaotech.team25.data.network.dto.DeletePaymentResponse
import com.kakaotech.team25.data.network.dto.PaymentResponse
import com.kakaotech.team25.data.network.remote.PaymentApiService
import javax.inject.Inject

class DefaultPaymentRepository @Inject constructor(
    private val paymentService: PaymentApiService
) {
    suspend fun requestPayment(payRequest: BillingKeyDto): Result<PaymentResponse> {
        return try {
            Log.d("PaymentRequest", "Requesting payment with: $payRequest")

            val response = paymentService.requestPay(payRequest)

            if (response.isSuccessful) {
                Log.d("PaymentResponse", "Response successful: ${response.body()}")
                Result.success(response.body()!!)
            } else {
                Log.e(
                    "PaymentResponseError",
                    "Response failed with code: ${response.code()}, message: ${response.message()}"
                )
                Result.failure(Exception("Payment failed with code: ${response.code()}, message: ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("PaymentRequestException", "Request failed with exception: ${e.message}", e)
            Result.failure(Exception("Payment request failed", e))
        }
    }


    suspend fun createBillingKey(createRequest: CreateBillingKeyRequest): Result<CreateBillingKeyResponse> {
        return try {
            Log.d("DefaultPaymentRepository", "Sending request to createBillingKey: $createRequest")
            val response = paymentService.createBillingKey(createRequest)
            if (response.isSuccessful) {
                Log.d("DefaultPaymentRepository", "Billing key creation response: ${response.body()}")
                Result.success(response.body()!!)
            } else {
                Log.e(
                    "DefaultPaymentRepository",
                    "Billing key creation failed with response: ${response.errorBody()?.string()}"
                )
                Result.failure(Exception("Payment failed: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Log.e("DefaultPaymentRepository", "Exception in createBillingKey: ${e.message}", e)
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

    suspend fun checkBillingKeyExists(): Result<BillingKeyExistsResponse> {
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
