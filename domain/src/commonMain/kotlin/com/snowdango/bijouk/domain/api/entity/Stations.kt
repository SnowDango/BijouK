package com.snowdango.bijouk.domain.api.entity

import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class Stations(
    val data: List<StationData>,
    val href: String,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class StationData(
    val id: String,
    val type: String,
    val href: String,
    val attributes: StationAttributes,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class StationAttributes(
    val isLive: Boolean,
    val requiresSubscription: Boolean,
    val kind: String,
    val radioUrl: String,
    val mediaKind: String,
    val name: String,
    val artwork: Artwork? = null,
    val url: String? = null,
    val playParams: PlayParams? = null,
)