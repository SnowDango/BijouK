package com.snowdango.bijouk.domain.api.entity.music.catalog

import kotlinx.serialization.Serializable

@Serializable
data class EditorialNotes(
    val short: String? = null,
    val standard: String? = null,
    val name: String? = null,
    val tagline: String? = null,
)
