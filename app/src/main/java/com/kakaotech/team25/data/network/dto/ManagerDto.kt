package com.kakaotech.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class ManagerDto(
    @SerializedName("managerId") val managerId : String,
    @SerializedName("name") val name : String,
    @SerializedName("profileImage") val profileImage : String,
    @SerializedName("career") val career : String,
    @SerializedName("comment") val comment : String
)
