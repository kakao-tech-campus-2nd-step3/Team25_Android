package com.example.team25.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.team25.dao.HospitalDao
import com.example.team25.domain.HospitalDomain

@Database(entities = [HospitalDomain::class], version = 1)
abstract class HospitalDatabase: RoomDatabase() {
    abstract fun hospitalDao(): HospitalDao

    companion object {
        @Volatile
        private var INSTANCE: HospitalDatabase? = null

        fun getInstance(context: Context): HospitalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HospitalDatabase::class.java,
                    "hospitals"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
