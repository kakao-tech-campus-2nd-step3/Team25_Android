package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.databinding.ActivityReservationStep7Binding
import com.example.team25.domain.HospitalDomain
import com.example.team25.ui.reservation.adapters.HospitalRecyclerViewAdapter
import com.example.team25.ui.reservation.interfaces.OnHospitalClickListener
import com.example.team25.ui.reservation.interfaces.SearchHospitalService
import com.example.team25.ui.reservation.network.KakaoApi
import com.example.team25.ui.reservation.network.RemoteSearchHospitalService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReservationStep7Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep7Binding
    private lateinit var searchHospitalService: SearchHospitalService
    private lateinit var hospitalRecyclerViewAdapter: HospitalRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeSearchHospitalService()
        setHospitalSearchListener()
        setSearchResultRecyclerView()
        navigateToPrevious()
    }

    private fun initializeSearchHospitalService() {
        searchHospitalService = RemoteSearchHospitalService()
    }

    private fun setHospitalSearchListener() {
        binding.destinationEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val keyword = s.toString()
                if (keyword.isNotBlank()) {
                    searchHospitals(keyword)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun searchHospitals(keyword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val hospitals = searchHospitalService.getSearchedResult(keyword, 1)
                val sortedHospitals = sortHospitals(hospitals, keyword)

                hospitalRecyclerViewAdapter.submitList(sortedHospitals)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /*
    정렬 기준
    1. 검색어가 이름에서 더 앞쪽에 나올수록 우선
    ( 검색어가 이름에 포함되어 있지 않은 경우 가장 낮은 우선순위 )

    ex)
    검색어: 부산대
    우선순위: 부산대학교 병원 > 양산부산대학교병원

    2. 글자수가 적은 항목이 우선
    */
    private fun sortHospitals(hospitals: List<HospitalDomain>, keyword: String): List<HospitalDomain> {
        return hospitals.sortedWith(compareBy(
            { if (it.name.indexOf(keyword) == -1) Int.MAX_VALUE else it.name.indexOf(keyword) },
            { it.name.length }
        ))
    }

    private fun setSearchResultRecyclerView() {
        val hospitalClickListener =
            object : OnHospitalClickListener {
                override fun onHospitalClicked() {
                    val intent = Intent(this@ReservationStep7Activity, ReservationStep7Activity::class.java)
                    startActivity(intent)
                }
            }

        hospitalRecyclerViewAdapter = HospitalRecyclerViewAdapter(hospitalClickListener)
        binding.hospitalRecyclerView.adapter = hospitalRecyclerViewAdapter
        binding.hospitalRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
