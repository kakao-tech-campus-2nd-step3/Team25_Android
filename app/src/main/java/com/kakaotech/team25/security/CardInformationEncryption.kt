package com.example.team25.security

import com.kakaotech.team25.ui.main.status.data.CardInfor
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class CardInformationEncryption {

    companion object {
        const val SECRET_KEY = "ac15e75f1cfb4ff49964d4fc4438a0b4" // 32 characters for AES-256 (16 characters for AES-128)
    }

    fun encryptCBC(card: CardInfor): String {
        val data = "cardNo=${card.getCardNumber()}&expYear=${card.getExpiredDate().substring(2, 4)}&expMonth=${card.getExpiredDate().substring(0, 2)}&idNo=${card.getBirth()}&cardPw=${card.getPassword()}"

        val keyBytes = SECRET_KEY.substring(0, 16).toByteArray(Charsets.UTF_8)
        val secretKey = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

        return encryptedData.joinToString("") { "%02x".format(it) }
    }
}
