package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.api.model.Artists
import com.snowdango.bijouk.api.model.LibraryArtists
import com.snowdango.bijouk.model.cider.data.entity.ArtistData

fun Artists.convert(): ArtistData {
    return ArtistData(
        id = id,
        name = attributes?.name.orEmpty(),
        artwork = attributes?.artwork?.convert().orEmpty(),
        href = href,
        catalog = null,
    )
}

fun LibraryArtists.convert(): ArtistData {
    return ArtistData(
        id = id,
        name = attributes?.name.orEmpty(),
        artwork = relationships?.catalog?.data?.map {
            it.attributes?.artwork?.convert()
        }?.firstOrNull { !it.isNullOrBlank() } ?: "",
        href = href,
        catalog = relationships?.catalog?.data?.firstOrNull()?.convert(),
    )
}