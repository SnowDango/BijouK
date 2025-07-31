package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.music.library.LibraryPlaylistFolders
import com.snowdango.bijouk.model.cider.data.entity.PlaylistFoldersData

fun LibraryPlaylistFolders.convert(): PlaylistFoldersData {
    return PlaylistFoldersData(
        id = id,
        href = href,
        name = attributes.name,
    )
}