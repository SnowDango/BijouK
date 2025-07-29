package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.SongData
import com.snowdango.bijouk.domain.api.entity.Songs
import com.snowdango.bijouk.model.cider.data.SearchSong

fun Songs.convertSearch(): List<SearchSong> {
    return data.map {
        it.convert()
    }
}

fun SongData.convert(): SearchSong {
    return SearchSong(
        id = id,
        name = attributes.name,
        artist = attributes.artistName.orEmpty(),
        album = attributes.albumName.orEmpty(),
        artwork = attributes.artwork?.convert().orEmpty(),
        href = href,
    )
}