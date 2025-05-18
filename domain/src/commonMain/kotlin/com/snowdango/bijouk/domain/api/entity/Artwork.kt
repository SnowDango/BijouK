package com.snowdango.bijouk.domain.api.entity

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Artwork(
    val height: Int?,
    val url: String,
    val width: Int?,
    val hasP3: Boolean? = null,
    val bgColor: String? = null,
    val textColor1: String? = null,
    val textColor2: String? = null,
    val textColor3: String? = null,
    val textColor4: String? = null,
    val gradient: Gradient? = null,
)
