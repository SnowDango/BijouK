package com.snowdango.bijouk.features.nowPlay.view.search.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.model.cider.data.SearchSong
import com.snowdango.bijouk.ui.component.SongCard

@Composable
fun SongsContent(
    songs: List<SearchSong>?,
    sheetSize: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (!songs.isNullOrEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    bottom = sheetSize,
                )
            ) {
                items(songs) {
                    SongCard(
                        artwork = it.artwork,
                        title = it.name,
                        artist = it.artist,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}
