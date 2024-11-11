package com.kakaotech.team25.data.network.remote

import com.kakaotech.team25.data.network.dto.BillingKeyDto
import com.kakaotech.team25.data.network.dto.BillingKeyExistsResponse
import com.kakaotech.team25.data.network.dto.CreateBillingKeyRequest
import com.kakaotech.team25.data.network.dto.CreateBillingKeyResponse
import com.kakaotech.team25.data.network.dto.DeletePaymentRequest
import com.kakaotech.team25.data.network.dto.DeletePaymentResponse
import com.kakaotech.team25.data.network.dto.PaymentResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentApiService {

    @POST("/api/payment/payment")
    suspend fun requestPay(
        @Body payRequest: BillingKeyDto
    ) : Response<PaymentResponse>
    @POST("/api/payment/billing-key")
    suspend fun createBillingKey(
        @Body createRequest: CreateBillingKeyRequest
    ) : Response<CreateBillingKeyResponse>
    @POST("/api/payment/billing-key/expire")
    suspend fun deleteBillingKey(
        @Body deletePaymentRequest: DeletePaymentRequest
    ) : Response<DeletePaymentResponse>
    @GET("/api/payment/billing-key/exists")
    suspend fun checkBillingKeyExists(): Response<BillingKeyExistsResponse>
}
