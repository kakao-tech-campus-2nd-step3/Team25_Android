package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class ManagerDto(
    @SerializedName("manager_id") val managerId : String,
    @SerializedName("name") val name : String,
    @SerializedName("profile_image") val profileImange : String,
    @SerializedName("career") val career : String,
    @SerializedName("comment") val comment : String
)
