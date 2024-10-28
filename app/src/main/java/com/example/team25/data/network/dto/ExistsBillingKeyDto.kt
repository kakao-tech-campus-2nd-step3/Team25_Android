package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class BillingKeyExistsResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Boolean
)
