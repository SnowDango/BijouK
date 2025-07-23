package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.Albums
import com.snowdango.bijouk.model.cider.data.SearchAlbum

fun Albums.convertSearch(): List<SearchAlbum> {
    return data.map {
        SearchAlbum(
            id = it.id,
            name = it.attributes?.name.orEmpty(),
            artist = it.attributes?.artistName.orEmpty(),
            artwork = it.attributes?.artwork?.convert().orEmpty(),
            href = it.href,
        )
    }
}
