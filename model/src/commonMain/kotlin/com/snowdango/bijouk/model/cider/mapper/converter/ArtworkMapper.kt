package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.api.model.Artwork
import com.snowdango.bijouk.api.model.LibraryArtwork
import com.snowdango.bijouk.api.model.RPCArtwork
import kotlin.jvm.JvmName


@JvmName("convertArtwork")
fun Artwork.convert(): String {
    return url
        ?.replace("{w}", width.toString())
        ?.replace("{h}", height.toString()) ?: ""
}

@JvmName("convertLibraryArtwork")
fun LibraryArtwork.convert(): String {
    return url
        .replace("{w}", (width ?: 0).toString())
        .replace("{h}", (width ?: 0).toString())
}

@JvmName("convertRPCArtwork")
fun RPCArtwork.convert(): String {
    return url
        ?.replace("{w}", (width ?: 0).toString())
        ?.replace("{h}", (height ?: 0).toString())
        .orEmpty()
}
