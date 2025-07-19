package com.snowdango.bijouk.model2.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.Artwork

fun Artwork.convert(): String {
    return url
        .replace("{w}", width?.toString() ?: "1000")
        .replace("{h}", height?.toString() ?: "1000")
}
