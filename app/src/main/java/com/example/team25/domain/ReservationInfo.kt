package com.example.team25.domain

data class ReservationInfo(
    val managerId: String,
    val departure: String,
    val destination: String,
    val serviceDate: String,
    val serviceType: String,
    val transportation: String,
    val price: Int,
    val patient: Patient
)