package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.api.model.LibraryPlaylists
import com.snowdango.bijouk.api.model.Playlists
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData

fun Playlists.convert(): PlaylistData {
    return PlaylistData(
        id = id,
        name = attributes?.name.orEmpty(),
        curatorName = attributes?.curatorName ?: "",
        description = attributes?.description?.standard,
        artwork = attributes?.artwork?.convert(),
        href = href,
        catalog = null,
    )
}

fun LibraryPlaylists.convert(): PlaylistData {
    return PlaylistData(
        id = id,
        name = attributes?.name.orEmpty(),
        curatorName = "",
        description = attributes?.description?.standard,
        artwork = attributes?.artwork?.convert()
            ?: relationships?.catalog?.data?.map {
                it.attributes?.artwork?.convert()
            }?.firstOrNull { !it.isNullOrEmpty() },
        href = href,
        catalog = relationships?.catalog?.data?.firstOrNull()?.convert(),
    )
}