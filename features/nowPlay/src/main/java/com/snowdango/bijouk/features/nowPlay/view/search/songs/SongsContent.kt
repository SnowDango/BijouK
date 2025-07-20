package com.snowdango.bijouk.features.nowPlay.view.search.songs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.features.nowPlay.component.SearchSongCard
import com.snowdango.bijouk.model.cider.data.SearchSong

@Composable
fun SongsContent(
    songs: List<SearchSong>?,
    sheetSize: Dp,
    onClickPlay: (id: String) -> Unit,
    onClickPlayNext: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    onClickPlayLater: (id: String) -> Unit,
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
                    SearchSongCard(
                        searchSong = it,
                        onClickPlay = onClickPlay,
                        onClickPlayNext = onClickPlayNext,
                        onClickPlayLater = onClickPlayLater,
                    )
                }
            }
        }
    }
}
