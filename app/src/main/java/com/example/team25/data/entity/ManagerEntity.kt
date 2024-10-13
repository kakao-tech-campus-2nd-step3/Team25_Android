package com.example.team25.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "managers")
class ManagerEntity (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("manager_id") val managerId : String,
    @ColumnInfo("name") val name : String,
    @ColumnInfo("profile_image") val profileImange : String,
    @ColumnInfo("career") val career : String,
    @ColumnInfo("comment") val comment : String
)
