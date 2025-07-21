package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.response.SearchResponse
import com.snowdango.bijouk.model.cider.data.SearchData
import com.snowdango.bijouk.model.cider.mapper.converter.convertSearch

fun SearchResponse.convert(): SearchData {
    return SearchData(
        playlists = data.results.playlists?.convertSearch(),
        albums = data.results.albums?.convertSearch(),
        songs = data.results.songs?.convertSearch(),
        artists = data.results.artists?.convertSearch(),
    )
}
