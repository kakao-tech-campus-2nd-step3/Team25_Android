package com.example.team25.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HospitalDomain(
    @SerializedName("place_name") val name: String,
    @SerializedName("road_address_name") val address: String,
):Parcelable
