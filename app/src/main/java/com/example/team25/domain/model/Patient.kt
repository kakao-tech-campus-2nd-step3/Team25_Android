package com.example.team25.domain.model

import com.example.team25.domain.Gender

data class Patient(
    val patientName: String,
    val patientPhone: String,
    val patientGender: Gender,
    val patientRelation: String,
    val patientBirth: String,
    val nokPhone: String
)
