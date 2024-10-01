package com.example.team25.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ManagerDomain(
    val name: String,
) : Parcelable
