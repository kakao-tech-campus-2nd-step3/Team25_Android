package com.kakaotech.team25.data.network.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HospitalDto(
    @SerializedName("id") val id: String,
    @SerializedName("place_name") val name: String,
    @SerializedName("road_address_name") val address: String,
) : Parcelable
