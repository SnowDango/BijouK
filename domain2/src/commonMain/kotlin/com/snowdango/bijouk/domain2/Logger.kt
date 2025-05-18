package com.snowdango.bijouk.domain2

expect class Logger constructor(tag: String) {

    fun w(tag: String? = null, message: String)

    fun e(tag: String? = null, message: String)

    fun e(tag: String? = null, throwable: Throwable)

    fun d(tag: String? = null, message: String)

    fun i(tag: String? = null, message: String)

    fun v(tag: String? = null, message: String)

}