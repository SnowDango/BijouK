package com.snowdango.bijouk.model.cider.mapper.converter


import com.snowdango.bijouk.domain.api.entity.music.catalog.Albums
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryAlbums
import com.snowdango.bijouk.model.cider.data.entity.AlbumData

fun Albums.convert(): AlbumData {
    return AlbumData(
        id = this.id,
        name = this.attributes.name,
        artist = attributes.artistName,
        artwork = attributes.artwork.convert(),
        href = href,
        genres = attributes.genreNames,
        copyRight = attributes.copyright,
        trackCount = attributes.trackCount,
        releaseYear = attributes.releaseDate?.split("-")?.firstOrNull().orEmpty(),
        catalog = null,
    )
}

fun LibraryAlbums.convert(): AlbumData {
    return AlbumData(
        id = this.id,
        name = this.attributes?.name.orEmpty(),
        artist = this.attributes?.artistName.orEmpty(),
        artwork = this.attributes?.artwork?.convert().orEmpty(),
        href = this.href,
        genres = attributes?.genreNames.orEmpty(),
        copyRight = null,
        trackCount = this.attributes?.trackCount ?: 0,
        releaseYear = this.attributes?.releaseDate?.split("-")?.firstOrNull().orEmpty(),
        catalog = this.relationships?.catalog?.data?.firstOrNull()?.convert(),
    )
}
