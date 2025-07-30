package com.snowdango.bijouk.domain.api.entity.music.library

import com.snowdango.bijouk.domain.api.entity.music.DescriptionAttribute
import com.snowdango.bijouk.domain.api.entity.music.PlayParameters
import kotlinx.serialization.Serializable


@Serializable
data class LibraryPlaylists(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes? = null,
    val relationships: Relationships? = null,
) {
    @Serializable
    data class Attributes(
        val artwork: LibraryArtwork? = null,
        val canEdit: Boolean,
        val dateAdded: String? = null,
        val description: DescriptionAttribute? = null,
        val hasCatalog: Boolean,
        val name: String,
        val playParams: PlayParameters? = null,
        val isPublic: Boolean,
        val trackTypes: List<String>? = null,
        val inFavorites: Boolean? = null,
    )

    @Serializable
    data object Relationships
}
