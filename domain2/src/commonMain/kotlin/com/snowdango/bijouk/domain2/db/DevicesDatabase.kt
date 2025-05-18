package com.snowdango.bijouk.domain2.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.snowdango.bijouk.domain2.db.dao.DevicesDao
import com.snowdango.bijouk.domain2.db.entity.DevicesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [DevicesEntity::class], version = 1, exportSchema = false)
@ConstructedBy(DevicesDatabaseConstructor::class)
abstract class DevicesDatabase : RoomDatabase() {

    abstract fun devicesDao(): DevicesDao
}

// Room compiler generates the `actual` implementations
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object DevicesDatabaseConstructor : RoomDatabaseConstructor<DevicesDatabase> {
    override fun initialize(): DevicesDatabase
}

fun RoomDatabase.Builder<DevicesDatabase>.devicesDatabaseBuild(): DevicesDatabase {
    return this
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
