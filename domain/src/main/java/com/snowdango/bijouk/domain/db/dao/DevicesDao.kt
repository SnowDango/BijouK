package com.snowdango.bijouk.domain.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.snowdango.bijouk.domain.db.entity.DevicesEntity


@Dao
interface DevicesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDevice(devicesEntity: DevicesEntity)

    @Query("select * from ${DevicesEntity.devices}")
    fun loadDevices(): List<DevicesEntity>

    @Query("DELETE FROM ${DevicesEntity.devices} WHERE id = :id")
    fun deleteById(id: Long)

}