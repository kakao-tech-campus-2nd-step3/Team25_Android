package com.example.team25.ui.reservation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.databinding.ActivityReservationStep9Binding
import com.example.team25.domain.ManagerDomain
import com.example.team25.ui.reservation.adapters.ManagerRecyclerViewAdapter
import com.example.team25.ui.reservation.interfaces.OnManagerClickListener

class ReservationStep9Activity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStep9Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationStep9Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setManagerRecyclerView()
        navigateToPrevious()
    }

    private fun setManagerRecyclerView() {
        val managerDomainList: ArrayList<ManagerDomain> = getListFromDb()
        val managerClickListener =
            object : OnManagerClickListener {
                override fun onManagerClicked() {
                    val intent = Intent(this@ReservationStep9Activity, ReservationStep10Activity::class.java)
                    startActivity(intent)
                }

            }

        val adapter = ManagerRecyclerViewAdapter(managerClickListener)

        binding.managerRecyclerView.adapter = adapter
        binding.managerRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter.submitList(managerDomainList)
    }

    private fun getListFromDb(): ArrayList<ManagerDomain> {
        val managerDomainLists: ArrayList<ManagerDomain> = arrayListOf(ManagerDomain("김지수"), ManagerDomain("임지수"),
            ManagerDomain("신지수"), ManagerDomain("이지수"), ManagerDomain("박지수")) // DB에서 데이터 받아옴
        return managerDomainLists
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
