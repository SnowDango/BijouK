package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.music.Artwork
import com.snowdango.bijouk.domain.api.entity.rpc.RPCArtwork

fun Artwork.convert(): String {
    return url
        .replace("{w}", width.toString())
        .replace("{h}", height.toString())
}

fun RPCArtwork.convert(): String {
    return url
        .replace("{w}", width.toString())
        .replace("{h}", height.toString())
}
