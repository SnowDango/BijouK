package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.music.catalog.Artists
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryArtists
import com.snowdango.bijouk.model.cider.data.entity.ArtistData

fun Artists.convert(): ArtistData {
    return ArtistData(
        id = id,
        name = attributes?.name.orEmpty(),
        artwork = attributes?.artwork?.convert().orEmpty(),
        href = href,
    )
}

fun LibraryArtists.convert(): ArtistData {
    return ArtistData(
        id = id,
        name = attributes?.name.orEmpty(),
        artwork = "",
        href = href,
    )
}