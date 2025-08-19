package com.snowdango.bijouk.domain.api.music.response

import kotlinx.serialization.Serializable


@Serializable
data class PlaylistResponse<T>(
    val `data`: Data<T>,
) {
    @Serializable
    data class Data<T>(
        val `data`: List<T>,
    )
}