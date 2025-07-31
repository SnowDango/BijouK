package com.snowdango.bijouk.model.cider.mapper.converter


import com.snowdango.bijouk.domain.api.entity.music.catalog.Stations
import com.snowdango.bijouk.model.cider.data.entity.StationData

fun Stations.convert(): StationData {
    return StationData(
        id = this.id,
        name = attributes.name,
        artwork = attributes.artwork.convert().orEmpty(),
        href = href,
    )
}