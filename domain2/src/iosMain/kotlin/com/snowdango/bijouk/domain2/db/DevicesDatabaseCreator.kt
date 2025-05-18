package com.snowdango.bijouk.domain2.db

import androidx.room.Room
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


fun getDevicesDatabase(): DevicesDatabase {
    val dbFilePath = documentDirectory() + "/device_database"
    return Room.databaseBuilder<DevicesDatabase>(name = dbFilePath)
        .devicesDatabaseBuild()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )

    return requireNotNull(documentDirectory?.path)
}