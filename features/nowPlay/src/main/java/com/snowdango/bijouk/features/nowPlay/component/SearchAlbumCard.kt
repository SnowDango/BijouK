package com.snowdango.bijouk.features.nowPlay.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowdango.bijouk.model.cider.data.SearchAlbum
import com.snowdango.bijouk.ui.component.AlbumCard


@Composable
fun SearchAlbumCard(
    searchAlbum: SearchAlbum,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        AlbumCard(
            album = searchAlbum.name,
            artist = searchAlbum.artist,
            artwork = searchAlbum.artwork,
        )
    }
}