package com.example.team25.data

import android.content.Context
import android.content.SharedPreferences

class Utils private constructor(context: Context) {

    companion object {
        private const val PREFS = "prefs"
        private const val ACCESS_TOKEN = "Access_Token"
        private const val REFRESH_TOKEN = "Refresh_Token"
        private var prefs: SharedPreferences? = null
        private var prefsEditor: SharedPreferences.Editor? = null
        private var instance: Utils? = null

        @Synchronized
        fun init(context: Context): Utils {
            if (instance == null) {
                instance = Utils(context)
            }
            return instance!!
        }

        fun setAccessToken(value: String) {
            prefsEditor?.putString(ACCESS_TOKEN, value)?.apply()
        }

        fun getAccessToken(defValue: String): String? {
            return prefs?.getString(ACCESS_TOKEN, defValue)
        }

        fun setRefreshToken(value: String) {
            prefsEditor?.putString(REFRESH_TOKEN, value)?.apply()
        }

        fun getRefreshToken(defValue: String): String? {
            return prefs?.getString(REFRESH_TOKEN, defValue)
        }

        fun clearToken() {
            prefsEditor?.clear()?.apply()
        }
    }

    init {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefsEditor = prefs?.edit()
    }
}
