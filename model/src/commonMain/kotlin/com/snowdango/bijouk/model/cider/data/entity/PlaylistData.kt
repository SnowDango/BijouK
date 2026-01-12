package com.snowdango.bijouk.model.cider.data.entity

data class PlaylistData(
    val id: String,
    val name: String,
    val curatorName: String,
    val description: String?,
    val artwork: String?,
    val href: String,
    val catalog: PlaylistData?,
)
