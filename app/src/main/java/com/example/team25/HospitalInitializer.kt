package com.example.team25

import android.content.Context
import com.example.team25.dao.HospitalDao
import com.example.team25.domain.HospitalDomain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import javax.inject.Inject

class HospitalInitializer @Inject constructor(
    private val context: Context,
    private val hospitalDao: HospitalDao,
    private val dispatcher: CoroutineDispatcher
) {
    fun initialize() {
        CoroutineScope(dispatcher).launch {
            val isDatabaseEmpty = hospitalDao.getAnyHospital() == null

            if (isDatabaseEmpty) {
                val inputStream = context.assets.open("hospital_data_202406.json")
                val reader = InputStreamReader(inputStream, "UTF-8")
                val hospitals: List<HospitalDomain> = Gson().fromJson(
                    reader, object : TypeToken<List<HospitalDomain>>() {}.type
                )
                hospitalDao.insertAll(hospitals)
            }
        }
    }
}
