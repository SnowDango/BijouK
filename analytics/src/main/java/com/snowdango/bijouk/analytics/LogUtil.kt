package com.snowdango.bijouk.analytics

import android.util.Log

class LogUtil {

    fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    companion object {
        private fun getTag(): String {
            val local = ThreadLocal<String>()
            return local.get() ?: "LogUtil"
        }

        fun d(message: String) {
            val logUtil = LogUtil()
            logUtil.d(getTag(), message)
        }

        fun e(message: String) {
            val logUtil = LogUtil()
            logUtil.e(getTag(), message)
        }
    }
}
