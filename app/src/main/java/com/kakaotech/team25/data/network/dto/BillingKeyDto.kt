package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class BillingKeyDto (
    @SerializedName("amount") val amount : Int?,
    @SerializedName("goodsName") val goodsName : String?,
    @SerializedName("cardQuota") val cardQuota : String?,
    @SerializedName("useShopInterest") val useShopInterest : Boolean?
)
data class PaymentResponse(
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: PaymentData?
)

data class PaymentData(
    @SerializedName("resultCode") val resultCode: String?,
    @SerializedName("resultMsg") val resultMsg: String?,
    @SerializedName("tid") val tid: String?,
    @SerializedName("orderId") val orderId: String?,
    @SerializedName("editDate") val editDate: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("paid") val paid: Boolean?,
    @SerializedName("paidAt") val paidAt: String?,
    @SerializedName("amount") val amount: Int?,
    @SerializedName("balanceAmt") val balanceAmt: Int?,
    @SerializedName("goodsName") val goodsName: String?,
    @SerializedName("useEscrow") val useEscrow: Boolean?,
    @SerializedName("currency") val currency: String?,
    @SerializedName("receiptUrl") val receiptUrl: String?
)
