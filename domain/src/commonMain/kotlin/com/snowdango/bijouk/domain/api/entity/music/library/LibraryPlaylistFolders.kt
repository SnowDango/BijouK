package com.snowdango.bijouk.domain.api.entity.music.library

import kotlinx.serialization.Serializable

@Serializable
data class LibraryPlaylistFolders(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes,
    
    ) {
    @Serializable
    data class Attributes(
        val dateAdded: String,
        val name: String,
    )
}
