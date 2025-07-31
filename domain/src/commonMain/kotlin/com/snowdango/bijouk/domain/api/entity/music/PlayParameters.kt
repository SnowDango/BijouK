package com.snowdango.bijouk.domain.api.entity.music

import kotlinx.serialization.Serializable

@Serializable
data class PlayParameters(
    val id: String,
    val kind: String,
)