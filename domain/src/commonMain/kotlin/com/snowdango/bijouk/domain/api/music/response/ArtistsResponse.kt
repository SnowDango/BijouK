package com.snowdango.bijouk.domain.api.music.response

import com.snowdango.bijouk.domain.api.entity.music.catalog.Artists
import kotlinx.serialization.Serializable


@Serializable
data class ArtistsResponse(
    val `data`: Data,
) {
    @Serializable
    data class Data(
        val `data`: List<Artists>,
    )
}


