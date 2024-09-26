package com.example.team25.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "hospitals")
data class HospitalDomain(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name")
    @SerializedName("place_name") val name: String,
    @ColumnInfo(name = "address")
    @SerializedName("road_address_name") val address: String,
) : Parcelable
