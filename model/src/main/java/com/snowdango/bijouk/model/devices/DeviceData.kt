package com.snowdango.bijouk.model.devices

data class DeviceData(
    val id: Long,
    val name: String,
    val baseUrl: String,
    val token: String,
)
