package com.snowdango.bijouk.domain.api.entity

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Singles(
    val href: String,
    val next: String? = null,
    val attributes: SinglesAttributes,
    val `data`: List<AlbumData>,
)


@Suppress("UnsafeOptInUsageError")
@Serializable
data class SinglesAttributes(
    val title: String,
)