package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.Artists
import com.snowdango.bijouk.model.cider.data.SearchArtist

fun Artists.convertSearch(): List<SearchArtist> {
    return data.map {
        SearchArtist(
            id = it.id,
            name = it.attributes.name,
            href = it.href,
            albums = it.relationships?.albums?.convertSearch().orEmpty(),
            artwork = it.attributes.artwork?.convert() ?: "",
        )
    }
}