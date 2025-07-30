package com.snowdango.bijouk.domain.api.entity.music.catalog

import kotlinx.serialization.Serializable

@Serializable
data class Preview(
    val artwork: Artwork? = null,
    val url: String,
    val hlsUrl: String? = null,
)
