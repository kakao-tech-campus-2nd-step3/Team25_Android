package com.example.team25.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.team25.dao.HospitalDao
import com.example.team25.domain.HospitalDomain

@Database(entities = [HospitalDomain::class], version = 1)
abstract class HospitalDatabase : RoomDatabase() {
    abstract fun hospitalDao(): HospitalDao
}
