package com.example.team25.ui.main.status

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team25.databinding.ActivityReservationStatusBinding
import com.example.team25.ui.main.status.adapters.ReservationHistoryRecyclerViewAdapter
import com.example.team25.ui.main.status.adapters.ReservationStatusRecyclerViewAdapter
import com.example.team25.ui.main.status.data.ReservationInfo
import com.example.team25.ui.main.status.interfaces.OnCheckReportClickListener
import com.example.team25.ui.main.status.interfaces.OnRequestCancelClickListener
import java.util.Date

class ReservationStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStatusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setReservationStatusRecyclerViewAdapter()
        setReservationHistoryRecyclerViewAdapter()
    }

    private fun setReservationStatusRecyclerViewAdapter() {
        val requestCancelClickListener = object : OnRequestCancelClickListener {
            override fun onRequestCancelClicked(item: ReservationInfo) {
                val intent = Intent(this@ReservationStatusActivity, ReservationCancelActivity::class.java)
                    .putExtra("ReservationInfo", item)
                startActivity(intent)
            }
        }
        val adapter = ReservationStatusRecyclerViewAdapter(requestCancelClickListener)

        binding.reservationStatusRecyclerView.adapter = adapter
        binding.reservationStatusRecyclerView.layoutManager = LinearLayoutManager(this)

        // mock 테스트
        adapter.submitList(createMock())
    }

    private fun setReservationHistoryRecyclerViewAdapter() {
        val checkReportClickListener = object : OnCheckReportClickListener {
            override fun onCheckReportClicked(item: ReservationInfo) {
                val intent = Intent(this@ReservationStatusActivity, ReservationCheckReportActivity::class.java)
                    .putExtra("ReservationInfo", item)
                startActivity(intent)
            }
        }

        val adapter = ReservationHistoryRecyclerViewAdapter(checkReportClickListener)

        binding.reservationHistoryRecyclerView.adapter = adapter
        binding.reservationHistoryRecyclerView.layoutManager = LinearLayoutManager(this)

        // mock 테스트
        adapter.submitList(createMock())
    }

    private fun createMock(): List<ReservationInfo> =
        listOf(
            ReservationInfo(id = "1", name = "김지수", date = Date()),
            ReservationInfo(id = "2", name = "홍길동", date = Date()),
        )

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
