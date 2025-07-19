package com.snowdango.bijouk.domain.api.request

import kotlinx.serialization.Serializable


@Suppress("UnsafeOptInUsageError")
@Serializable
data class PlayRequestBody(
    val type: String,
    val id: String,
) {
    companion object {
        fun create(type: PlayType, id: String): PlayRequestBody {
            return PlayRequestBody(
                type.type,
                id,
            )
        }
    }

    enum class PlayType(val type: String) {
        Songs("songs"),
        Playlists("playlists"),
        Albums("albums"),
        Artists("artists"),
    }
}
