package com.snowdango.bijouk.domain.api.music.response

import kotlinx.serialization.Serializable

@Serializable
data class RelationshipViewResponse<T>(
    val attributes: Attributes,
    val data: List<T>,
    val meta: Meta? = null,
    val next: String? = null,
) {

    @Serializable
    data object Attributes

    @Serializable
    data object Meta
}