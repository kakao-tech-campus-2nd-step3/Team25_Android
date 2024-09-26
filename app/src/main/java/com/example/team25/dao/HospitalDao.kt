package com.example.team25.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.team25.entity.HospitalEntity

@Dao
interface HospitalDao {
    @Insert
    suspend fun insertAll(hospitals: List<HospitalEntity>)

    @Query("SELECT * FROM hospitals LIMIT 1")
    suspend fun getAnyHospital(): HospitalEntity?

    @Query("SELECT * FROM hospitals WHERE name LIKE '%' || :keyword || '%' LIMIT :pageSize OFFSET :offset")
<<<<<<< HEAD
    suspend fun searchHospitalsByName(
        keyword: String,
        pageSize: Int,
        offset: Int,
    ): List<HospitalDomain>
=======
    suspend fun searchHospitalsByName(keyword: String, pageSize: Int, offset: Int): List<HospitalEntity>
>>>>>>> 1f2f7165179825c3718d26a1bd430d0e3f3146de
}
