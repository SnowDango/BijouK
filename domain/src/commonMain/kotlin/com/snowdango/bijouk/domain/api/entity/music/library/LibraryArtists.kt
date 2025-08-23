package com.snowdango.bijouk.domain.api.entity.music.library

import com.snowdango.bijouk.domain.api.entity.music.catalog.Artists
import kotlinx.serialization.Serializable


@Serializable
data class LibraryArtists(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes? = null,
    val relationships: Relationships? = null,
) {

    @Serializable
    data class Attributes(
        val inFavorites: Boolean? = null,
        val name: String,
    )

    @Serializable
    data class Relationships(
        val catalog: Catalog? = null,
    ) {
        @Serializable
        data class Catalog(
            val href: String? = null,
            val data: List<Artists>,
        )
    }

}
