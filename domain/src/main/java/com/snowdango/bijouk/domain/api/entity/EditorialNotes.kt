package com.snowdango.bijouk.domain.api.entity

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class EditorialNotes(
    val name: String? = null,
    val short: String,
    val standard: String,
)
