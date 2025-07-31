package com.snowdango.bijouk.domain.api.entity.music.catalog

import com.snowdango.bijouk.domain.api.entity.music.PlayParameters
import kotlinx.serialization.Serializable


@Serializable
data class Stations(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes,
    val relationships: Relationships? = null,
) {
    @Serializable
    data class Attributes(
        val artwork: Artwork,
        val durationInMillis: Long? = null,
        val editorialNotes: EditorialNotes? = null,
        val episodeNumber: String? = null,
        val contentRating: String? = null,
        val isLive: Boolean,
        val mediaKind: String,
        val name: String,
        val playParams: PlayParameters? = null,
        val stationProviderName: String? = null,
        val url: String,
    )

    @Serializable
    data object Relationships
}
