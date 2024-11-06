package com.kakaotech.team25.ui.main.status.interfaces

import com.kakaotech.team25.domain.model.ReservationInfo

interface OnRequestCancelClickListener {
    fun onRequestCancelClicked(item: ReservationInfo)
}
