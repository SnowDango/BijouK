package com.snowdango.bijouk.domain.api.entity

import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class FullAlbums(
    val href: String,
    val next: String? = null,
    val attributes: FullAlbumsAttributes,
    val `data`: List<AlbumData>,
)


@Suppress("UnsafeOptInUsageError")
@Serializable
data class FullAlbumsAttributes(
    val title: String,
)
