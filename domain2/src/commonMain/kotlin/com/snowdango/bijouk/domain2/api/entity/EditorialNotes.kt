package com.snowdango.bijouk.domain2.api.entity

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class EditorialNotes(
    val name: String? = null,
    val short: String? = null,
    val standard: String? = null,
)
