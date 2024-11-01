package com.example.team25.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.team25.data.entity.ManagerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ManagerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManagers(managers: List<ManagerEntity>)

    @Query("SELECT * FROM managers")
    fun getAllManagers(): Flow<List<ManagerEntity>>
}
