package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.api.model.RPCArtwork
import com.snowdango.bijouk.domain.api.entity.music.catalog.Artwork
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryArtwork

fun Artwork.convert(): String {
    return url
        .replace("{w}", width.toString())
        .replace("{h}", height.toString())
}

fun LibraryArtwork.convert(): String {
    return url
        .replace("{w}", (width ?: 0).toString())
        .replace("{h}", (width ?: 0).toString())
}

fun RPCArtwork.convert(): String {
    return url
        ?.replace("{w}", (width ?: 0).toString())
        ?.replace("{h}", (height ?: 0).toString())
        .orEmpty()
}
