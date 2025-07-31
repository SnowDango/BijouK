package com.snowdango.bijouk.domain.api.entity.music.library

import kotlinx.serialization.Serializable

@Serializable
data class LibraryArtwork(
    val bgColor: String? = null,
    val height: Int? = null,
    val width: Int? = null,
    val textColor1: String? = null,
    val textColor2: String? = null,
    val textColor3: String? = null,
    val textColor4: String? = null,
    val url: String,
)