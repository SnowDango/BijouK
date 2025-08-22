package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.entity.music.catalog.Artists
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryArtists
import com.snowdango.bijouk.domain.api.music.response.ArtistsResponse
import com.snowdango.bijouk.model.cider.data.ArtistDetailData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import kotlin.jvm.JvmName


@JvmName("convertArtistsResponse")
fun ArtistsResponse<Artists>.convert(): ArtistDetailData? {
    val data = data.data.firstOrNull() ?: return null
    return ArtistDetailData(
        id = data.id,
        href = data.href,
        genres = data.attributes?.genreNames.orEmpty(),
        name = data.attributes?.name.orEmpty(),
        artwork = data.attributes?.artwork?.convert().orEmpty(),
        url = data.attributes?.url.orEmpty(),
        stations = data.relationships?.stations?.data?.map { it.convert() }.orEmpty(),
    )
}

@JvmName("convertLibraryArtistsResponse")
fun ArtistsResponse<LibraryArtists>.convert(): ArtistDetailData? {
    val data = data.data.firstOrNull() ?: return null
    return ArtistDetailData(
        id = data.id,
        href = data.href,
        genres = emptyList(),
        name = data.attributes?.name.orEmpty(),
        artwork = "",
        url = "",
        stations = emptyList(),
    )
}