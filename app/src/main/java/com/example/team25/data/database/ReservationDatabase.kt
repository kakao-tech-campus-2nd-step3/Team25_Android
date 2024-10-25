package com.example.team25.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.team25.data.dao.ReservationDao
import com.example.team25.data.entity.ReservationEntity

@Database(entities = [ReservationEntity::class], version = 1)
@TypeConverters(PatientConverter::class)
abstract class ReservationDatabase: RoomDatabase() {
    abstract fun reservationDao(): ReservationDao
}
