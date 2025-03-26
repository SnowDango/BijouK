package com.snowdango.bijouk.repository.device

import com.snowdango.bijouk.domain.db.DevicesDatabase
import com.snowdango.bijouk.domain.db.entity.DevicesEntity

class DevicesRepository(
    private val devicesDatabase: DevicesDatabase,
) {
    fun saveDevice(devicesEntity: DevicesEntity) {
        devicesDatabase.devicesDao().saveDevice(devicesEntity)
    }

    fun loadDevices(): List<DevicesEntity> {
        return devicesDatabase.devicesDao().loadDevices()
    }
}
