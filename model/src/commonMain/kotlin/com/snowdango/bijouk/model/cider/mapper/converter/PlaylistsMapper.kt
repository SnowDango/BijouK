package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.Playlists
import com.snowdango.bijouk.model.cider.data.SearchPlaylist

fun Playlists.convertSearch(): List<SearchPlaylist> {
    return data.map {
        SearchPlaylist(
            id = it.id,
            name = it.attributes.name,
            curatorName = it.attributes.curatorName,
            description = it.attributes.description?.standard,
            artwork = it.attributes.artwork?.convert(),
            href = href,
        )
    }
}