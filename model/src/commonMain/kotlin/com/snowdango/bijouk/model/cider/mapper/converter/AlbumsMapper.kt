package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.Albums
import com.snowdango.bijouk.domain.api.entity.FullAlbums
import com.snowdango.bijouk.domain.api.entity.Singles
import com.snowdango.bijouk.model.cider.data.SearchAlbum
import com.snowdango.bijouk.model.cider.data.entity.Album

fun Albums.convertSearch(): List<SearchAlbum> {
    return data.map {
        SearchAlbum(
            id = it.id,
            name = it.attributes?.name.orEmpty(),
            artist = it.attributes?.artistName.orEmpty(),
            artwork = it.attributes?.artwork?.convert().orEmpty(),
            href = it.href,
        )
    }
}

fun Singles.convert(): List<Album> {
    return data.map {
        Album(
            id = it.id,
            name = it.attributes?.name.orEmpty(),
            artist = it.attributes?.artistName.orEmpty(),
            artwork = it.attributes?.artwork?.convert().orEmpty(),
            href = it.href,
            genres = it.attributes?.genreNames,
            copyRight = it.attributes?.copyright,
            trackCount = it.attributes?.trackCount ?: 0,
        )
    }
}

fun FullAlbums.convert(): List<Album> {
    return data.map {
        Album(
            id = it.id,
            name = it.attributes?.name.orEmpty(),
            artist = it.attributes?.artistName.orEmpty(),
            artwork = it.attributes?.artwork?.convert().orEmpty(),
            href = it.href,
            genres = it.attributes?.genreNames,
            copyRight = it.attributes?.copyright,
            trackCount = it.attributes?.trackCount ?: 0,
        )
    }
}
