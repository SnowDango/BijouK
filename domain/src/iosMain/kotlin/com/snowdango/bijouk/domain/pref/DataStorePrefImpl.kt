package com.snowdango.bijouk.domain.pref

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


class IosDataStorePrefImpl : DataStorePrefImpl() {

    @OptIn(ExperimentalForeignApi::class)
    fun getIosDataStorePath(dataStoreName: String): String {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory).path + "/$dataStoreName"
    }

    override fun getDataStorePath(dataStoreName: String): String {
        return getIosDataStorePath(dataStoreName)
    }
}
