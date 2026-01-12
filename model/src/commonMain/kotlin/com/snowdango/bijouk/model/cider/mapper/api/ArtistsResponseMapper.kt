package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.api.model.ArtistsResponse
import com.snowdango.bijouk.api.model.LibraryArtistsResponse
import com.snowdango.bijouk.model.cider.data.ArtistDetailData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import kotlin.jvm.JvmName


@JvmName("convertArtistsResponse")
fun ArtistsResponse.convert(): ArtistDetailData? {
    val data = data.data?.firstOrNull() ?: return null
    return ArtistDetailData(
        id = data.id,
        href = data.href,
        genres = data.attributes?.genreNames.orEmpty(),
        name = data.attributes?.name.orEmpty(),
        artwork = data.attributes?.artwork?.convert().orEmpty(),
        url = data.attributes?.url.orEmpty(),
        stations = data.relationships?.defaultPlayableContent?.data?.map { it.convert() }.orEmpty(),
    )
}

@JvmName("convertLibraryArtistsResponse")
fun LibraryArtistsResponse.convert(): ArtistDetailData? {
    val data = data.data?.firstOrNull() ?: return null
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