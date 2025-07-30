package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.music.response.ArtistsTopSongResponse
import com.snowdango.bijouk.model.cider.data.entity.Song
import com.snowdango.bijouk.model.cider.mapper.converter.convert


fun ArtistsTopSongResponse.convert(): List<Song>? {
    return data.data?.map { song ->
        Song(
            id = song.id,
            name = song.attributes.name,
            album = song.attributes.albumName.orEmpty(),
            artist = song.attributes.artistName.orEmpty(),
            artwork = song.attributes.artwork?.convert().orEmpty(),
            href = song.href,
            genres = song.attributes.genreNames,
            hasLyrics = song.attributes.hasLyrics,
            composerName = song.attributes.composerName,
        )
    }
}