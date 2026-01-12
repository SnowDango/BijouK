package com.snowdango.bijouk.model.cider.mapper.api

import com.snowdango.bijouk.api.model.RelationshipViewAlbumsResponse
import com.snowdango.bijouk.api.model.RelationshipViewLibraryAlbumsResponse
import com.snowdango.bijouk.api.model.RelationshipViewSongsResponse
import com.snowdango.bijouk.model.cider.data.entity.AlbumData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.model.cider.mapper.converter.convert
import kotlin.jvm.JvmName


@JvmName("convertAlbumsResponse")
fun RelationshipViewAlbumsResponse.convert(): List<AlbumData> {
    return this.data.data.map {
        it.convert()
    }
}

@JvmName("convertLibraryAlbumsResponse")
fun RelationshipViewLibraryAlbumsResponse.convert(): List<AlbumData> {
    return this.data.data.map {
        it.convert()
    }
}

@JvmName("convertSongsResponse")
fun RelationshipViewSongsResponse.convert(): List<SongData> {
    return this.data.data.map {
        it.convert()
    }
}