package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

class UserRoleDto (
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: UserRole?,
)

data class UserRole(
    @SerializedName("userRole") val userRole: String?
)
