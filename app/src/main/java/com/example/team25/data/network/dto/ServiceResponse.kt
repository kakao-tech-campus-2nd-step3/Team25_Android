package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class ServiceResponse<T>(
    @SerializedName("data") val data: T?,
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: Boolean
)
