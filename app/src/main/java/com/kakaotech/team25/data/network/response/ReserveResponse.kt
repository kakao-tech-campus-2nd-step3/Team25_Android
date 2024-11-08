package com.kakaotech.team25.data.network.response

import com.google.gson.annotations.SerializedName

data class ReserveResponse(
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: ReservationData?
) {
    data class ReservationData(
        @SerializedName("reservationId") val reservationId: Int?,
        @SerializedName("managerId") val managerId: Int?,
        @SerializedName("departureLocation") val departureLocation: String?,
        @SerializedName("arrivalLocation") val arrivalLocation: String?,
        @SerializedName("reservationDateTime") val reservationDateTime: String?,
        @SerializedName("serviceType") val serviceType: String?,
        @SerializedName("transportation") val transportation: String?,
        @SerializedName("price") val price: Int?,
        @SerializedName("reservationStatus") val reservationStatus: String?,
        @SerializedName("patient") val patient: Patient?
    )

    data class Patient(
        @SerializedName("name") val name: String?,
        @SerializedName("phoneNumber") val phoneNumber: String?,
        @SerializedName("patientGender") val patientGender: String?,
        @SerializedName("patientRelation") val patientRelation: String?,
        @SerializedName("birthDate") val birthDate: String?,
        @SerializedName("nokPhone") val nokPhone: String?
    )
}
