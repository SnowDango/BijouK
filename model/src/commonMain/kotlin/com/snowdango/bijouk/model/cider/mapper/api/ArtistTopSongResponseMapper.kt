package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.response.ArtistsTopSongResponse
import com.snowdango.bijouk.model.cider.data.entity.Song
import com.snowdango.bijouk.model.cider.mapper.converter.convert


fun ArtistsTopSongResponse.convert(): List<Song>? {
    return data.data?.map { song ->
        Song(
            id = song.id,
            name = song.attributes.name,
            album = song.attributes.albumName,
            artist = song.attributes.artistName,
            artwork = song.attributes.artwork.convert() ?: "",
            href = song.href,
            genres = song.attributes.genreNames,
            hasLyrics = song.attributes.hasLyrics,
            composerName = song.attributes.composerName,
        )
    }
}