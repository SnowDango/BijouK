package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.Songs
import com.snowdango.bijouk.domain.api.entity.TopSongs
import com.snowdango.bijouk.model.cider.data.SearchSong
import com.snowdango.bijouk.model.cider.data.entity.Song

fun Songs.convertSearch(): List<SearchSong> {
    return data.map {
        SearchSong(
            id = it.id,
            name = it.attributes.name,
            artist = it.attributes.artistName,
            album = it.attributes.albumName,
            artwork = it.attributes.artwork.convert(),
            href = it.href,
        )
    }
}

fun TopSongs.convert(): List<Song> {
    return data.map {
        Song(
            id = it.id,
            name = it.attributes.name,
            artist = it.attributes.artistName,
            album = it.attributes.albumName,
            artwork = it.attributes.artwork.convert(),
            href = it.href,
            genres = it.attributes.genreNames,
            hasLyrics = it.attributes.hasLyrics,
            composerName = it.attributes.composerName,
        )
    }
}