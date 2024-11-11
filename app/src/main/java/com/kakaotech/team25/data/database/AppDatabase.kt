package com.kakaotech.team25.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kakaotech.team25.data.dao.ManagerDao
import com.kakaotech.team25.data.dao.ReservationDao
import com.kakaotech.team25.data.entity.ManagerEntity
import com.kakaotech.team25.data.entity.ReservationEntity

@Database(
    entities = [ManagerEntity::class, ReservationEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun managerDao(): ManagerDao
    abstract fun reservationDao(): ReservationDao
}
