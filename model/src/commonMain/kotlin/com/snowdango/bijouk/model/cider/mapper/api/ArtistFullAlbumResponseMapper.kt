package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.music.response.ArtistFullAlbumResponse
import com.snowdango.bijouk.model.cider.data.entity.Album
import com.snowdango.bijouk.model.cider.mapper.converter.convert

fun ArtistFullAlbumResponse.convert(): List<Album>? {
    return data.data?.map { album ->
        Album(
            id = album.id,
            name = album.attributes?.name ?: "",
            artist = album.attributes?.artistName ?: "",
            artwork = album.attributes?.artwork?.convert() ?: "",
            href = album.href,
            genres = album.attributes?.genreNames ?: emptyList(),
            copyRight = album.attributes?.copyright ?: "",
            trackCount = album.attributes?.trackCount ?: 0,
            releaseYear = album.attributes?.releaseDate?.split("-")?.firstOrNull() ?: "",
        )
    }
}