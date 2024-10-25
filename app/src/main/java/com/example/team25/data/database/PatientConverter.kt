package com.example.team25.data.database

import androidx.room.TypeConverter
import com.example.team25.domain.model.Patient
import com.google.gson.Gson

class PatientConverter {
    @TypeConverter
    fun fromPatient(patient: Patient): String {
        return Gson().toJson(patient)
    }

    @TypeConverter
    fun toPatient(patientJson: String): Patient {
        return Gson().fromJson(patientJson, Patient::class.java)
    }
}
