package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.databinding.ActivityReservationStep7Binding
import com.example.team25.domain.HospitalDomain
import com.example.team25.ui.reservation.adapters.HospitalRecyclerViewAdapter
import com.example.team25.ui.reservation.interfaces.OnHospitalClickListener
import com.example.team25.ui.reservation.network.KakaoApi
import com.example.team25.ui.reservation.network.RemoteSearchHospitalService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReservationStep7Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep7Binding
    private lateinit var remoteService: RemoteSearchHospitalService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeRemoteSearchHospitalService()
        setSearchResultRecyclerView()
        navigateToPrevious()
    }

    private fun initializeRemoteSearchHospitalService() {
        val retrofitService = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KakaoApi::class.java)

        remoteService = RemoteSearchHospitalService(retrofitService)
    }

    private fun setSearchResultRecyclerView() {
        val hospitals = mutableListOf<HospitalDomain>()

        repeat(20) {
            hospitals.add(HospitalDomain("병원${it + 1}", "부산광역시 서구 구덕로 ${it + 170}"))
        }

        val hospitalClickListener =
            object : OnHospitalClickListener {
                override fun onHospitalClicked() {
                    val intent = Intent(this@ReservationStep7Activity, ReservationStep8Activity::class.java)
                    startActivity(intent)
                }
            }

        val adapter = HospitalRecyclerViewAdapter(hospitalClickListener)
        binding.hospitalRecyclerView.adapter = adapter
        binding.hospitalRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter.submitList(hospitals)
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
