package com.kakaotech.team25.data.network.services

import com.kakaotech.team25.ui.main.status.data.CardInfor
import javax.inject.Inject

class CardService
    @Inject
    constructor() {
        fun createCardInfo(
            cardNumber: String,
            expireDate: String,
            password: String,
            birth: String,
        ): CardInfor {
            return CardInfor(cardNumber, expireDate, password, birth)
        }
    }
