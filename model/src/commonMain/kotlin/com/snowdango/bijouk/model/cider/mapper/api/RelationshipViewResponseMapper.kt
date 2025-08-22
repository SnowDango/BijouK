package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.domain.api.entity.music.catalog.Albums
import com.snowdango.bijouk.domain.api.entity.music.catalog.Songs
import com.snowdango.bijouk.domain.api.entity.music.library.LibraryAlbums
import com.snowdango.bijouk.domain.api.music.response.RelationshipViewResponse
import com.snowdango.bijouk.model.cider.data.entity.AlbumData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import kotlin.jvm.JvmName


@JvmName("convertAlbumsResponse")
fun RelationshipViewResponse<Albums>.convert(): List<AlbumData> {
    return this.data.data.map {
        it.convert()
    }
}

@JvmName("convertLibraryAlbumsResponse")
fun RelationshipViewResponse<LibraryAlbums>.convert(): List<AlbumData> {
    return this.data.data.map {
        it.convert()
    }
}

@JvmName("convertSongsResponse")
fun RelationshipViewResponse<Songs>.convert(): List<SongData> {
    return this.data.data.map {
        it.convert()
    }
}