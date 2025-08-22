package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.music.catalog.Songs
import com.snowdango.bijouk.domain.api.entity.music.library.LibrarySongs
import com.snowdango.bijouk.model.cider.data.entity.SongData


fun Songs.convert(): SongData {
    return SongData(
        id = id,
        name = attributes.name,
        album = attributes.albumName,
        artist = attributes.artistName,
        artwork = attributes.artwork.convert(),
        href = href,
        genres = attributes.genreNames,
        hasLyrics = attributes.hasLyrics,
        composerName = attributes.composerName,
        catalog = null,
    )
}

fun LibrarySongs.convert(): SongData {
    return SongData(
        id = id,
        name = attributes?.name ?: "",
        album = attributes?.albumName ?: "",
        artist = attributes?.artistName ?: "",
        artwork = attributes?.artwork?.convert().orEmpty(),
        href = href,
        genres = attributes?.genreNames ?: emptyList(),
        hasLyrics = attributes?.hasLyrics ?: false,
        composerName = null,
        catalog = relationships?.catalog?.data?.firstOrNull()?.convert()
    )
}