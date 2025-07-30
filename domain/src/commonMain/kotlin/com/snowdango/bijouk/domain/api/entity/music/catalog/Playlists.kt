package com.snowdango.bijouk.domain.api.entity.music.catalog

import com.snowdango.bijouk.domain.api.entity.music.Artwork
import com.snowdango.bijouk.domain.api.entity.music.DescriptionAttribute
import com.snowdango.bijouk.domain.api.entity.music.PlayParameters
import kotlinx.serialization.Serializable

@Serializable
data class Playlists(
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
        val curatorName: String? = null,
        val description: DescriptionAttribute? = null,
        val isChart: Boolean,
        val lastModifiedDate: String? = null,
        val name: String,
        val playlistType: String,
        val playParams: PlayParameters? = null,
        val url: String,
        val trackTypes: List<String>? = null,
        val inFavorites: Boolean? = null,
    )

    @Serializable
    data object Relationships

    @Serializable
    data object Views
}
