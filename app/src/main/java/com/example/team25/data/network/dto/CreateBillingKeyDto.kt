package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class CreateBillingKeyRequest(
    @SerializedName("encData") val encData : String,
    @SerializedName("cardAlias") val cardAlias : String,
)
data class CreateBillingKeyResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: CreateBillingKeyData
)

data class CreateBillingKeyData(
    @SerializedName("resultCode") val resultCode: String,
    @SerializedName("resultMsg") val resultMsg: String,
    @SerializedName("bid") val bid: String,
    @SerializedName("authDate") val authDate: String,
    @SerializedName("cardCode") val cardCode: String,
    @SerializedName("cardName") val cardName: String,
    @SerializedName("tid") val tid: String,
    @SerializedName("orderId") val orderId: String
)
