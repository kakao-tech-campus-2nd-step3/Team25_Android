package com.kakaotech.team25.ui.reservation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kakaotech.team25.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationActivity : AppCompatActivity() {
    private val reservationViewModel: ReservationInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ReservationStep1Fragment())
                .commit()
        }
    }
}
