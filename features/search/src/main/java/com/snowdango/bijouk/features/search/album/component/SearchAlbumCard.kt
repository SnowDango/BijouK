package com.snowdango.bijouk.features.search.album.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.features.search.R
import com.snowdango.bijouk.model.cider.data.SearchAlbum
import com.snowdango.bijouk.ui.component.AlbumCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchAlbumCard(
    searchAlbum: SearchAlbum,
    onClickPlay: (id: String) -> Unit,
    onClickPlayNext: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    onClickPlayLater: (id: String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        AlbumCard(
            album = searchAlbum.name,
            artist = searchAlbum.artist,
            artwork = searchAlbum.artwork,
            modifier = Modifier
                .combinedClickable(
                    onLongClick = {
                        expanded = true
                    },
                    onClick = {},
                ),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.search_dropdown_menu_play)) },
                onClick = {
                    onClickPlay.invoke(searchAlbum.id)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.search_dropdown_menu_play_next)) },
                onClick = {
                    onClickPlayNext.invoke(searchAlbum.id)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.search_dropdown_menu_play_later)) },
                onClick = {
                    onClickPlayLater.invoke(searchAlbum.id)
                    expanded = false
                }
            )
        }
    }
}
