package com.snowdango.bijouk.analytics

import com.snowdango.bijouk.analytics.logger.Logger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object LogUtil : KoinComponent {

    private val logger: Logger by inject()
    private const val defaultTag = "LogUtil"

    var isWriteSocketEventLog: Boolean = false

    fun w(tag: String? = null, message: String) {
        val autoTag = logger.autoTag(defaultTag)
        logger.w(tag ?: autoTag, message)
    }

    fun e(tag: String? = null, message: String) {
        val autoTag = logger.autoTag(defaultTag)
        logger.e(tag ?: autoTag, message)
    }

    fun e(tag: String? = null, throwable: Throwable) {
        val autoTag = logger.autoTag(defaultTag)
        logger.e(tag ?: autoTag, throwable)
    }

    fun d(tag: String? = null, message: String) {
        val autoTag = logger.autoTag(defaultTag)
        logger.d(tag ?: autoTag, message)
    }

    fun i(tag: String? = null, message: String) {
        val autoTag = logger.autoTag(defaultTag)
        logger.i(tag ?: autoTag, message)
    }

    fun v(tag: String? = null, message: String) {
        val autoTag = logger.autoTag(defaultTag)
        logger.v(tag ?: autoTag, message)

    }

    fun socketDebug(tag: String? = null, message: String) {
        if (isWriteSocketEventLog) {
            d(tag, message)
        }
    }

}