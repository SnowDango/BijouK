package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.music.response.ArtistsResponse
import com.snowdango.bijouk.model.cider.data.ArtistDetailData
import com.snowdango.bijouk.model.cider.mapper.converter.convert

fun ArtistsResponse.convert(): ArtistDetailData? {
    val data = data.data.firstOrNull() ?: return null
    return ArtistDetailData(
        id = data.id,
        href = data.href,
        genres = data.attributes?.genreNames.orEmpty(),
        name = data.attributes?.name.orEmpty(),
        artwork = data.attributes?.artwork?.convert().orEmpty(),
        url = data.attributes?.url.orEmpty(),
        stations = data.relationships?.stations?.map { it.convert() }.orEmpty(),
    )
}