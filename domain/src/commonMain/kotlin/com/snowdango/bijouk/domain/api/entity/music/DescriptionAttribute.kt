package com.snowdango.bijouk.domain.api.entity.music

import kotlinx.serialization.Serializable

@Serializable
data class DescriptionAttribute(
    val short: String? = null,
    val standard: String,
)