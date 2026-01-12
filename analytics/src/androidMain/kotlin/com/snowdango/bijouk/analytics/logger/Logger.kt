package com.snowdango.bijouk.analytics.logger

import android.os.Build
import android.util.Log
import java.util.regex.Pattern

actual class Logger actual constructor() {

    actual fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    actual fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    actual fun e(tag: String, throwable: Throwable) {
        Log.e(tag, "Throwable: $throwable CAUSE ${throwable.cause}")
    }

    actual fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    actual fun v(tag: String, message: String) {
        Log.v(tag, message)
    }

    actual fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    actual fun autoTag(defaultTag: String): String {
        val thread = Thread.currentThread().stackTrace

        return if (thread.size >= CALL_STACK_INDEX) {
            thread[CALL_STACK_INDEX].run {
                "${createStackElementTag(className)}\$$methodName"
            }
        } else {
            defaultTag
        }
    }

    private val anonymousClass = Pattern.compile("(\\$\\d+)+$")

    fun createStackElementTag(className: String): String {
        var tag = className
        val m = anonymousClass.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1)
        // Tag length limit was removed in API 24.
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tag
        } else tag.take(MAX_TAG_LENGTH)
    }


    companion object {
        private const val MAX_LOG_LENGTH = 4000
        private const val MAX_TAG_LENGTH = 23
        private const val CALL_STACK_INDEX = 9
    }

}