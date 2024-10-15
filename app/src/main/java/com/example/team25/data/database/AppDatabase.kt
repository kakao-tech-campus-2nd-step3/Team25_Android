package com.example.team25.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.team25.data.dao.HospitalDao
import com.example.team25.data.dao.ManagerDao
import com.example.team25.data.dao.ReservationDao
import com.example.team25.data.entity.HospitalEntity
import com.example.team25.data.entity.ManagerEntity
import com.example.team25.data.entity.ReservationEntity

@Database(
    entities = [HospitalEntity::class, ManagerEntity::class, ReservationEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hospitalDao(): HospitalDao
    abstract fun managerDao(): ManagerDao
    abstract fun reservationDao(): ReservationDao
}
