package com.snowdango.bijouk.domain.api.entity.music.catalog

import kotlinx.serialization.Serializable

@Serializable
data class Artwork(
    val bgColor: String? = null,
    val height: Int,
    val width: Int,
    val textColor1: String? = null,
    val textColor2: String? = null,
    val textColor3: String? = null,
    val textColor4: String? = null,
    val url: String,
)