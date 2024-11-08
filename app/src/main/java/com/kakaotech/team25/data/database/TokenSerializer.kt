package com.kakaotech.team25.data.database

import androidx.datastore.core.Serializer
import com.kakaotech.team25.TokensProto.Tokens
import java.io.InputStream
import java.io.OutputStream

object TokenSerializer : Serializer<Tokens> {
    override val defaultValue: Tokens = Tokens.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Tokens {
        return Tokens.parseFrom(input)
    }

    override suspend fun writeTo(t: Tokens, output: OutputStream) {
        t.writeTo(output)
    }
}
