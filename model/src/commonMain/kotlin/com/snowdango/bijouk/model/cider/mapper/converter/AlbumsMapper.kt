package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.AlbumData
import com.snowdango.bijouk.domain.api.entity.Albums
import com.snowdango.bijouk.model.cider.data.SearchAlbum

fun Albums.convertSearch(): List<SearchAlbum> {
    return data.map {
        it.convert()
    }
}

fun AlbumData.convert(): SearchAlbum {
    return SearchAlbum(
        id = id,
        name = attributes?.name.orEmpty(),
        artist = attributes?.artistName.orEmpty(),
        artwork = attributes?.artwork?.convert().orEmpty(),
        href = href,
    )
}
