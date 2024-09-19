package com.example.team25.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.team25.domain.HospitalDomain

@Dao
interface HospitalDao {
    @Insert
    suspend fun insertAll(hospitals: List<HospitalDomain>)

    @Query("SELECT * FROM hospitals LIMIT 1")
    suspend fun getAnyHospital(): HospitalDomain?

    @Query("SELECT * FROM hospitals WHERE name LIKE '%' || :keyword || '%' LIMIT :pageSize OFFSET :offset")
    suspend fun searchHospitalsByName(keyword: String, pageSize: Int, offset: Int): List<HospitalDomain>
}
