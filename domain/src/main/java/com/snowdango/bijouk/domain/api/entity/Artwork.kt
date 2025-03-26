package com.snowdango.bijouk.domain.api.entity

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Artwork(
    val height: Int?,
    val url: String,
    val width: Int?,
    val hasP3: Boolean? = null,
)
