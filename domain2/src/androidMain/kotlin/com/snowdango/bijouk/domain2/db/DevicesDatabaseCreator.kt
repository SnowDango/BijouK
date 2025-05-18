package com.snowdango.bijouk.domain2.db

import android.content.Context
import androidx.room.Room


fun getDevicesDatabase(context: Context): DevicesDatabase {
    return Room.databaseBuilder(context, DevicesDatabase::class.java, "device_database")
        .devicesDatabaseBuild()
}