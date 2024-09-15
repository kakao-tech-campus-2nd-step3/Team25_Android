package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.databinding.ActivityReservationStep7Binding
import com.example.team25.domain.HospitalDomain
import com.example.team25.ui.reservation.adapters.HospitalRecyclerViewAdapter
import com.example.team25.ui.reservation.interfaces.OnHospitalClickListener

class ReservationStep7Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep7Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSearchResultRecyclerView()
        navigateToPrevious()
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
