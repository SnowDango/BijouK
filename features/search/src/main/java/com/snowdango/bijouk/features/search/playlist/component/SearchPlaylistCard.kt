package com.snowdango.bijouk.features.search.playlist.component

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
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData
import com.snowdango.bijouk.ui.component.PlaylistCard

@Composable
fun SearchPlaylistCard(
    searchPlaylist: PlaylistData,
    onClickPlay: (String) -> Unit,
    onClickPlayNext: (id: String) -> Unit,
    onClickPlayLater: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        PlaylistCard(
            name = searchPlaylist.name,
            editor = searchPlaylist.curatorName,
            thumbnail = searchPlaylist.artwork,
            trackThumbs = null,
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
                    onClickPlay.invoke(searchPlaylist.id)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.search_dropdown_menu_play_next)) },
                onClick = {
                    onClickPlayNext.invoke(searchPlaylist.id)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.search_dropdown_menu_play_later)) },
                onClick = {
                    onClickPlayLater.invoke(searchPlaylist.id)
                    expanded = false
                }
            )
        }
    }
}
