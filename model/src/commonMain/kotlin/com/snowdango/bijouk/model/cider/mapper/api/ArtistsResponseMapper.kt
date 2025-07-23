package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.response.ArtistsResponse
import com.snowdango.bijouk.model.cider.data.ArtistDetailData
import com.snowdango.bijouk.model.cider.mapper.converter.convert

fun ArtistsResponse.convert(): ArtistDetailData? {
    val data = data.data.firstOrNull() ?: return null
    return ArtistDetailData(
        id = data.id,
        href = data.href,
        genres = data.attributes.genreNames,
        name = data.attributes.name,
        artwork = data.attributes.artwork?.convert() ?: "",
        url = data.attributes.url,
        stations = data.relationships.stations?.convert() ?: emptyList(),
    )
}