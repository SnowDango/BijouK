package com.snowdango.bijouk.analytics.logger

expect class Logger() {

    fun w(tag: String, message: String)

    fun e(tag: String, message: String)

    fun e(tag: String, throwable: Throwable)

    fun d(tag: String, message: String)

    fun i(tag: String, message: String)

    fun v(tag: String, message: String)

    fun autoTag(defaultTag: String): String

}