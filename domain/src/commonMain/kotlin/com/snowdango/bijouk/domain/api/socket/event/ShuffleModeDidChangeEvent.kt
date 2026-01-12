package com.snowdango.bijouk.domain.api.socket.event

import kotlinx.serialization.Serializable

@Serializable
data class ShuffleModeDidChangeEvent(
    val type: String,
    val data: Int,
)
