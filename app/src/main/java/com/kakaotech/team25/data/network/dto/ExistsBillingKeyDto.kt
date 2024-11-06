package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class BillingKeyExistsResponse(
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: BiilingKeyExistsResult?
)

data class BiilingKeyExistsResult(
    @SerializedName("exists") val exists: Boolean?,
    @SerializedName("cardName") val cardName : String?
)
