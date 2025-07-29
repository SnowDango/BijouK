package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.PlaylistData
import com.snowdango.bijouk.domain.api.entity.Playlists
import com.snowdango.bijouk.model.cider.data.SearchPlaylist

fun Playlists.convertSearch(): List<SearchPlaylist> {
    return data.map {
        it.convert()
    }
}

fun PlaylistData.convert(): SearchPlaylist {
    return SearchPlaylist(
        id = id,
        name = attributes.name,
        curatorName = attributes.curatorName ?: "",
        description = attributes.description?.standard,
        artwork = attributes.artwork?.convert(),
        href = href,
    )
}