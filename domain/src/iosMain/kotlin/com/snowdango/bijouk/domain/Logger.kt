package com.snowdango.bijouk.domain

import platform.Foundation.NSLog

actual class Logger actual constructor(tag: String) {

    val parentTag: String = tag

    actual fun v(tag: String?, message: String) {
        NSLog("VERBOSE: [${tag ?: parentTag}] $message")
    }

    actual fun d(tag: String?, message: String) {
        NSLog("DEBUG: [${tag ?: parentTag}] $message")
    }

    actual fun i(tag: String?, message: String) {
        NSLog("INFO: [${tag ?: parentTag}] $message")
    }

    actual fun w(tag: String?, message: String) {
        NSLog("WARN: [${tag ?: parentTag}] $message")
    }

    actual fun e(tag: String?, message: String) {
        NSLog("ERROR: [${tag ?: parentTag}] $message")
    }

    actual fun e(tag: String?, throwable: Throwable) {
        NSLog("ERROR: [${tag ?: parentTag}] Throwable: $throwable CAUSE ${throwable.cause}")
    }

}