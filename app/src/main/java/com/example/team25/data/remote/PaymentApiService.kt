package com.example.team25.data.network.remote

import com.example.team25.data.network.dto.BillingKeyDto
import com.example.team25.data.network.dto.BillingKeyExistsResponse
import com.example.team25.data.network.dto.CreateBillingKeyRequest
import com.example.team25.data.network.dto.CreateBillingKeyResponse
import com.example.team25.data.network.dto.DeletePaymentRequest
import com.example.team25.data.network.dto.DeletePaymentResponse
import com.example.team25.data.network.dto.PaymentResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentApiService {

    @POST("https://ollagaljido.net/api/payment/payment")
    suspend fun requestPay(
        @Body payRequest: BillingKeyDto
    ) : Response<PaymentResponse>
    @POST("https://ollagaljido.net/api/payment/billing-key")
    suspend fun createBillingKey(
        @Body createRequest: CreateBillingKeyRequest
    ) : Response<CreateBillingKeyResponse>
    @POST("https://ollagaljido.net/api/payment/billing-key/expire")
    suspend fun deleteBillingKey(
        @Body deletePaymentRequest: DeletePaymentRequest
    ) : Response<DeletePaymentResponse>
    @GET("https://ollagaljido.net/api/payment/billing-key/exists")
    suspend fun checkBillingKeyExists(): Response<BillingKeyExistsResponse>
}
