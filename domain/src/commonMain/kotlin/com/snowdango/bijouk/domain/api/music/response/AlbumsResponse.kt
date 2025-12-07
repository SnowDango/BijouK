package com.snowdango.bijouk.domain.api.music.response

import kotlinx.serialization.Serializable


@Serializable
data class AlbumsResponse<T>(
    val `data`: Data<T>,
) {
    @Serializable
    data class Data<T>(
        val `data`: List<T>,
    )
}
