package com.snowdango.bijouk.domain.api.entity.music.catalog

import com.snowdango.bijouk.domain.api.entity.music.Artwork
import kotlinx.serialization.Serializable

@Serializable
data class Preview(
    val artwork: Artwork? = null,
    val url: String,
    val hlsUrl: String? = null,
)
