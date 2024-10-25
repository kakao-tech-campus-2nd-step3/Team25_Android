package com.example.team25.domain.model

import android.os.Parcelable
import com.example.team25.domain.Gender
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient(
    val patientName: String,
    val patientPhone: String,
    val patientGender: Gender,
    val patientRelation: String,
    val patientBirth: String,
    val nokPhone: String,
) : Parcelable
