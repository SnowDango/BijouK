package com.snowdango.bijouk.model.devices

import com.snowdango.bijouk.domain.db.entity.DevicesEntity
import com.snowdango.bijouk.repository.device.DevicesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DevicesModel : KoinComponent {

    private val devicesRepository: DevicesRepository by inject()

    suspend fun saveDevice(
        name: String,
        host: String,
        port: Int?,
        ssl: Boolean,
        token: String,
    ) {
        withContext(Dispatchers.IO) {
            devicesRepository.saveDevice(
                DevicesEntity(
                    name = name,
                    host = host,
                    port = port,
                    ssl = ssl,
                    token = token,
                )
            )
        }
    }

    suspend fun loadDevice(): List<DeviceData> {
        return withContext(Dispatchers.IO) {
            devicesRepository.loadDevices().map { entity ->
                val baseUrl = if (entity.ssl) {
                    "https://"
                } else {
                    "http://"
                } + if (entity.port == null) {
                    entity.host
                } else {
                    "${entity.host}:${entity.port}"
                }
                DeviceData(
                    id = entity.id,
                    name = entity.name,
                    baseUrl = baseUrl,
                    token = entity.token
                )
            }
        }
    }
}
