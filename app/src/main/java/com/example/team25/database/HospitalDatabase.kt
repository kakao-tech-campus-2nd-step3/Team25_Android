package com.example.team25.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.team25.dao.HospitalDao
import com.example.team25.entity.HospitalEntity

@Database(entities = [HospitalEntity::class], version = 1)
abstract class HospitalDatabase : RoomDatabase() {
    abstract fun hospitalDao(): HospitalDao
}
