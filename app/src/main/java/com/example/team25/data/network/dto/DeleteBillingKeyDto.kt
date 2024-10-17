package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

// Request DTO
data class DeletePaymentRequest(
    @SerializedName("orderId")
    val orderId: String
)

// Response DTO
data class DeletePaymentResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: DeletePaymentResult
)

data class DeletePaymentResult(
    @SerializedName("resultCode") val resultCode: String,
    @SerializedName("resultMsg") val resultMsg: String,
    @SerializedName("tid") val tid: String,
    @SerializedName("orderId") val orderId: String,
    @SerializedName("bid") val bid: String,
    @SerializedName("authDate") val authDate: String
)
