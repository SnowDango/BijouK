package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.entity.music.catalog.Albums
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryAlbums
import com.snowdango.bijouk.domain.api.music.response.AlbumsResponse
import com.snowdango.bijouk.model.cider.data.AlbumDetailData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import kotlin.jvm.JvmName


@JvmName("AlbumResponseMapper")
fun AlbumsResponse<Albums>.convert(): AlbumDetailData? {
    val data = data.data.firstOrNull() ?: return null
    return AlbumDetailData(
        id = data.id,
        title = data.attributes.name,
        artist = data.attributes.artistName,
        artwork = data.attributes.artwork.convert(),
        releaseDate = data.attributes.releaseDate.orEmpty(),
        trackCount = data.attributes.trackCount,
        genres = data.attributes.genreNames,
    )
}

@JvmName("LibraryAlbumResponseMapper")
fun AlbumsResponse<LibraryAlbums>.convert(): AlbumDetailData? {
    val data = data.data.firstOrNull() ?: return null
    return AlbumDetailData(
        id = data.id,
        title = data.attributes?.name.orEmpty(),
        artist = data.attributes?.artistName.orEmpty(),
        artwork = data.attributes?.artwork?.convert(),
        releaseDate = data.attributes?.releaseDate.orEmpty(),
        trackCount = data.attributes?.trackCount ?: 0,
        genres = data.attributes?.genreNames.orEmpty(),
    )
}