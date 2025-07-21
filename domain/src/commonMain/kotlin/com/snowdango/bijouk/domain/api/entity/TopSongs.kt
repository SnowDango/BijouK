package com.snowdango.bijouk.domain.api.entity

import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class TopSongs(
    val href: String,
    val next: String? = null,
    val attributes: TopSongAttributes,
    val `data`: List<SongData>,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class TopSongAttributes(
    val title: String,
)
