package com.example.team25.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.team25.data.dao.ManagerDao
import com.example.team25.data.entity.ManagerEntity

@Database(entities = [ManagerEntity::class], version = 1)
abstract class ManagerDatabase: RoomDatabase() {
    abstract fun managerDao(): ManagerDao
}
