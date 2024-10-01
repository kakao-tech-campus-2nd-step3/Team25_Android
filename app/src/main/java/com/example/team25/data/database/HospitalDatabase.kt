package com.example.team25.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.team25.data.dao.HospitalDao
import com.example.team25.data.entity.HospitalEntity

@Database(entities = [HospitalEntity::class], version = 1, exportSchema = false)
abstract class HospitalDatabase : RoomDatabase() {
    abstract fun hospitalDao(): HospitalDao
}
