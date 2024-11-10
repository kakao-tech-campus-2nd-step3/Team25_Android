package com.kakaotech.team25.security

import com.kakaotech.team25.BuildConfig
import com.kakaotech.team25.ui.main.status.data.CardInfor
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class CardInformationEncryption @Inject constructor(){

    companion object {
         val SECRET_KEY = BuildConfig.CARD_SECRET_KEY
    }

    fun encryptCBC(card: CardInfor): String {
        val data = "cardNo=${card.getCardNumber()}&expYear=${
            card.getExpiredDate().substring(2, 4)
        }&expMonth=${card.getExpiredDate().substring(0, 2)}&idNo=${card.getBirth()}&cardPw=${card.getPassword()}"

        val keyBytes = SECRET_KEY.toByteArray(Charsets.UTF_8)
        if (keyBytes.size != 32) {
            throw IllegalArgumentException("SECRET_KEY must be 32 bytes for AES-256 encryption")
        }

        val secretKey = SecretKeySpec(keyBytes, "AES")
        val ivBytes = keyBytes.copyOfRange(0, 16)
        val ivSpec = IvParameterSpec(ivBytes)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)
        val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

        return encryptedData.joinToString("") { "%02x".format(it) }
    }
}
