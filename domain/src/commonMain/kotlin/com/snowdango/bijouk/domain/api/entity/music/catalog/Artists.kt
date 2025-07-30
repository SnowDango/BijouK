package com.snowdango.bijouk.domain.api.entity.music.catalog

import com.snowdango.bijouk.domain.api.entity.music.Artwork
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Artists(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes? = null,
    val relationships: Relationships? = null,
    val views: Views? = null,
) {
    @Serializable
    data class Attributes(
        val artwork: Artwork? = null,
        val editorialNotes: EditorialNotes? = null,
        val genreNames: List<String>,
        val inFavorites: Boolean? = null,
        val name: String,
        val url: String,
    )

    @Serializable
    data class Relationships(
        @SerialName("default-playable-content")
        val stations: Stations? = null,
    )

    @Serializable
    data object Views
}
