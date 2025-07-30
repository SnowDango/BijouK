package com.snowdango.bijouk.domain.api.entity.rpc

import kotlinx.serialization.Serializable


@Serializable
data class RPCAlbums(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes? = null,
) {
    @Serializable
    data class Attributes(
        val artistName: String,
        val artwork: RPCArtwork? = null,
        val audioTraits: List<String>? = null,
        val copyright: String? = null,
        val genreNames: List<String>,
        val isCompilation: Boolean? = null,
        val isComplete: Boolean? = null,
        val isMasteredForItunes: Boolean? = null,
        val isSingle: Boolean? = null,
        val name: String,
        val RPCPlayParams: RPCPlayParams? = null,
        val recordLabel: String? = null,
        val releaseDate: String? = null,
        val trackCount: Int,
        val upc: String? = null,
        val url: String? = null,
    )
}
