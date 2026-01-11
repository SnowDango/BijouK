package com.snowdango.bijouk.model.cider.mapper.converter


import com.snowdango.bijouk.api.model.Stations
import com.snowdango.bijouk.model.cider.data.entity.StationData

fun Stations.convert(): StationData {
    return StationData(
        id = this.id,
        name = attributes.name,
        artwork = attributes.artwork.convert().orEmpty(),
        href = href,
    )
}