package com.snowdango.bijouk.model.cider.mapper.converter

import com.snowdango.bijouk.domain.api.entity.Stations
import com.snowdango.bijouk.model.cider.data.entity.Station


fun Stations.convert(): List<Station> {
    return data.map {
        Station(
            id = it.id,
            name = it.attributes.name,
            href = it.href,
            artwork = it.attributes.artwork?.convert() ?: "",
        )
    }
}