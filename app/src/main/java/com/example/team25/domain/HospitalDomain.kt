package com.example.team25.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HospitalDomain(
    val placeId: String,
    val name: String,
    val address: String
) : Parcelable
