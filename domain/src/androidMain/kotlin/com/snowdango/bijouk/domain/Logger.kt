package com.snowdango.bijouk.domain

import android.util.Log

actual class Logger actual constructor(tag: String) {

    val parentTag: String = tag

    actual fun d(tag: String?, message: String) {
        Log.d(tag ?: parentTag, message)
    }

    actual fun e(tag: String?, message: String) {
        Log.e(tag ?: parentTag, message)
    }

    actual fun e(tag: String?, throwable: Throwable) {
        Log.e(tag ?: parentTag, "Throwable: $throwable CAUSE ${throwable.cause}")
    }

    actual fun i(tag: String?, message: String) {
        Log.i(tag ?: parentTag, message)
    }

    actual fun v(tag: String?, message: String) {
        Log.v(tag ?: parentTag, message)
    }

    actual fun w(tag: String?, message: String) {
        Log.w(tag ?: parentTag, message)
    }

}