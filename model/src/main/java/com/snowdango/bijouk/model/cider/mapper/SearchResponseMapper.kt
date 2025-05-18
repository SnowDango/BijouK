package com.snowdango.bijouk.model.cider.mapper


import com.snowdango.bijouk.domain.api.entity.Albums
import com.snowdango.bijouk.domain.api.entity.Artists
import com.snowdango.bijouk.domain.api.entity.Playlists
import com.snowdango.bijouk.domain.api.entity.Songs
import com.snowdango.bijouk.domain.api.response.SearchResponse
import com.snowdango.bijouk.model.cider.data.SearchAlbum
import com.snowdango.bijouk.model.cider.data.SearchArtist
import com.snowdango.bijouk.model.cider.data.SearchData
import com.snowdango.bijouk.model.cider.data.SearchPlaylist
import com.snowdango.bijouk.model.cider.data.SearchSong
import com.snowdango.bijouk.model.cider.mapper.converter.convert

fun SearchResponse.convert(): SearchData {
    return SearchData(
        playlists = data.results.playlists?.convert(),
        albums = data.results.albums?.convert(),
        songs = data.results.songs?.convert(),
        artists = data.results.artists?.convert(),
    )
}

fun Artists.convert(): List<SearchArtist> {
    return data.map {
        SearchArtist(
            id = it.id,
            name = it.attributes.name,
            href = it.href,
            albums = it.relationships.albums.convert(),
            artwork = it.attributes.artwork.convert(),
        )
    }
}

fun Songs.convert(): List<SearchSong> {
    return data.map {
        SearchSong(
            id = it.id,
            name = it.attributes.name,
            artist = it.attributes.artistName,
            album = it.attributes.albumName,
            artwork = it.attributes.artwork.convert(),
            href = it.href,
        )
    }
}

fun Albums.convert(): List<SearchAlbum> {
    return data.map {
        SearchAlbum(
            id = it.id,
            name = it.attributes?.name.orEmpty(),
            artist = it.attributes?.artistName.orEmpty(),
            artwork = it.attributes?.artwork?.convert().orEmpty(),
            href = it.href,
        )
    }
}

fun Playlists.convert(): List<SearchPlaylist> {
    return data.map {
        SearchPlaylist(
            id = it.id,
            name = it.attributes.name,
            description = it.attributes.description.standard,
            artwork = it.attributes.artwork.convert(),
            href = href,
        )
    }
}
