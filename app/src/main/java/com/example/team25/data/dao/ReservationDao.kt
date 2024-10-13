package com.example.team25.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.team25.data.entity.ReservationEntity
import com.example.team25.domain.model.ReservationStatus

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservations(reservations: List<ReservationEntity>)

    @Query("SELECT * FROM reservations WHERE reservation_status LIKE '%' || :status || '%'")
    suspend fun getReservationsByStatus(status: ReservationStatus): List<ReservationEntity>
}
