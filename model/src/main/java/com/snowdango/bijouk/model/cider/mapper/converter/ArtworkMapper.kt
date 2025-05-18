package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain2.api.entity.Artwork


fun Artwork.convert(): String {
    return url
        .replace("{w}", width?.toString() ?: "1000")
        .replace("{h}", height?.toString() ?: "1000")
}

fun com.snowdango.bijouk.domain.api.entity.Artwork.convert(): String {
    return url
        .replace("{w}", width?.toString() ?: "1000")
        .replace("{h}", height?.toString() ?: "1000")
}

