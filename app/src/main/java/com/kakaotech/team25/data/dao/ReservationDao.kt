package com.kakaotech.team25.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kakaotech.team25.data.entity.ReservationEntity
import com.kakaotech.team25.domain.ReservationStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReservations(reservations: List<ReservationEntity>)

    @Query("SELECT * FROM reservations")
    fun getAllReservationsFlow(): Flow<List<ReservationEntity>>

    @Query("SELECT * FROM reservations WHERE reservation_status LIKE '%' || :status || '%'")
    suspend fun getReservationsByStatus(status: ReservationStatus): List<ReservationEntity>
}
