package com.snowdango.bijouk.features.device.qr.entity

import kotlinx.serialization.Serializable


@Serializable
data class QRCodeInfo(
    val address: String,
    val token: String,
    val method: String,
    val initialData: InitialData,
)

@Serializable
data class InitialData(
    val version: String,
    val platform: String,
    val os: String,
)