package com.snowdango.bijouk.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.snowdango.bijouk.domain.db.dao.DevicesDao
import com.snowdango.bijouk.domain.db.entity.DevicesEntity

@Database(entities = [DevicesEntity::class], version = 1, exportSchema = false)
abstract class DevicesDatabase: RoomDatabase() {

    abstract fun devicesDao(): DevicesDao

    companion object {
        @Volatile
        private var INSTANCE: DevicesDatabase? = null

        fun getDatabase(context: Context): DevicesDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, DevicesDatabase::class.java, "device_database")
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}