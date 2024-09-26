package com.example.team25.security

import com.example.team25.ui.main.status.data.CardInfor
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

class CardInformationEncryption {
    companion object {
        const val SECRET_KEY = "2dcc2a0d63bf4694"
    }

    private val SECRET_IV = byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    fun encryptCBC(card: CardInfor): String {
        val str = "cardNo=${card.getCardNumber()}&expYear=${card.getExpiredDate().substring(2,4)}&expMonth=${card.getExpiredDate().substring(0,2)}&idNo=${card.getBirth()}&cardPw=${card.getPassword()}" // 암호화할 문자열
        val iv = IvParameterSpec(SECRET_IV)
        val key = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)

        val crypted = cipher.doFinal(str.toByteArray())

        val encodedByte = Base64.getEncoder().encode(crypted)

        return String(encodedByte)
    }

}
