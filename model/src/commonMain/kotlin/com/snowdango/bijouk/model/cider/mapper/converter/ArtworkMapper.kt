package com.snowdango.bijouk.model.cider.mapper.converter

fun Artwork.convert(): String {
    return url
        .replace("{w}", width?.toString() ?: "1000")
        .replace("{h}", height?.toString() ?: "1000")
}
