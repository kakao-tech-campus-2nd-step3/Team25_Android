package com.kakaotech.team25.data.network.typeadapter

import android.util.Log
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateTypeAdapter(): TypeAdapter<LocalDateTime>() {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun write(out: JsonWriter?, value: LocalDateTime?) {
        if (value == null) {
            out?.nullValue()
        } else {
            out?.value(value.format(formatter))
        }
    }

    override fun read(`in`: JsonReader?): LocalDateTime? {
        return if (`in`?.peek() == com.google.gson.stream.JsonToken.NULL) {
            Log.e("LocalDateTypeAdapter", "Read Null")
            `in`.nextNull()
            null
        } else {
            val dateTimeString = `in`?.nextString()
            try {
                LocalDateTime.parse(dateTimeString, formatter)
            } catch (e: DateTimeParseException) {
                Log.e("LocalDateTypeAdapter", "Failed to parse date: $dateTimeString", e)
                null
            }
        }
    }
}
