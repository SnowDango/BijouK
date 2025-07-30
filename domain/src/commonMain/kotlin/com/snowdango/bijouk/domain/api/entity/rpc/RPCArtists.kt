package com.snowdango.bijouk.domain.api.entity.rpc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class RPCArtists(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes? = null,
    val relationships: RPCPlayParameters? = null,
) {
    @Serializable
    data class Attributes(
        val artwork: RPCArtwork? = null,
        val genreNames: List<String> = emptyList(),
        val name: String,
        val url: String? = null,
    )

    @Serializable
    data class Relationships(
        val albums: RPCAlbums? = null,
        @SerialName("default-playable-content")
        val stations: RPCStations? = null,
    )
}
