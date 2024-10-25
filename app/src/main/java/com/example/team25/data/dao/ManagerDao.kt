package com.example.team25.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.team25.data.entity.ManagerEntity

@Dao
interface ManagerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManagers(managers: List<ManagerEntity>)

    @Query("SELECT * FROM managers")
    suspend fun getAllManagers(): List<ManagerEntity>

    @Query("SELECT * FROM managers WHERE name LIKE '%' || :name || '%'")
    suspend fun getManagersByName(name: String): List<ManagerEntity>

    @Query("DELETE FROM managers")
    suspend fun clearManagers()
}
