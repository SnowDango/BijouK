package com.snowdango.bijouk.domain.api.entity.rpc


import kotlinx.serialization.Serializable


@Serializable
data class RPCStations(
    val id: String,
    val type: String,
    val href: String,
    val attributes: Attributes? = null,
) {
    @Serializable
    data class Attributes(
        val isLive: Boolean,
        val requiresSubscription: Boolean,
        val kind: String,
        val radioUrl: String,
        val mediaKind: String,
        val name: String,
        val artwork: RPCArtwork? = null,
        val url: String? = null,
        val playParams: RPCPlayParameters? = null,
    )
}
