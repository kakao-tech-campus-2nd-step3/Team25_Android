package com.example.team25.ui.reservation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.team25.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationActivity : AppCompatActivity() {
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
