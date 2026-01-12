package com.snowdango.bijouk.analytics.logger

import platform.Foundation.NSLog
import platform.Foundation.NSThread

actual class Logger actual constructor() {

    actual fun v(tag: String, message: String) {
        NSLog("VERBOSE: [${tag}] $message")
    }

    actual fun d(tag: String, message: String) {
        NSLog("DEBUG: [${tag}] $message")
    }

    actual fun i(tag: String, message: String) {
        NSLog("INFO: [${tag}] $message")
    }

    actual fun w(tag: String, message: String) {
        NSLog("WARN: [${tag}] $message")
    }

    actual fun e(tag: String, message: String) {
        NSLog("ERROR: [${tag}] $message")
    }

    actual fun e(tag: String, throwable: Throwable) {
        NSLog("ERROR: [${tag}] Throwable: $throwable CAUSE ${throwable.cause}")
    }

    actual fun autoTag(defaultTag: String): String {
        val symbols = NSThread.callStackSymbols
        if (symbols.size <= CALL_STACK_INDEX) return defaultTag

        return (symbols[CALL_STACK_INDEX] as? String)?.let {
            createStackElementTag(it)
        } ?: defaultTag
    }

    fun createStackElementTag(string: String): String {
        var tag = string
        tag = tag.substringBeforeLast('$')
        tag = tag.substringBeforeLast('(')
        if (tag.contains("$")) {
            // coroutines
            tag = tag.substring(tag.lastIndexOf(".", tag.lastIndexOf(".") - 1) + 1)
            tag = tag.replace("$", "")
        } else {
            // others
            tag = tag.substringAfterLast(".")
            tag = tag.replace("#", ".")
        }
        return tag
    }

    companion object {
        private const val MAX_LOG_LENGTH = 4000
        private const val MAX_TAG_LENGTH = 23
        private const val CALL_STACK_INDEX = 8
    }

}