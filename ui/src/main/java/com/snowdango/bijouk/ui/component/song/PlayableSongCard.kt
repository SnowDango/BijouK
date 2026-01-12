package com.snowdango.bijouk.ui.component.song

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Queue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.ui.R

@Composable
fun PlayableSongCard(
    song: SongData,
    index: Int? = null,
    isShowArtwork: Boolean = true,
    sideMargin: Dp = 48.dp,
    onClickPlay: (id: String) -> Unit,
    onClickPlayNext: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    onClickPlayLater: (id: String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        positionalThreshold = SwipeToDismissBoxDefaults.positionalThreshold,
    )

    LaunchedEffect(swipeToDismissBoxState.currentValue) {
        when (swipeToDismissBoxState.currentValue) {
            SwipeToDismissBoxValue.StartToEnd -> {
                onClickPlayNext.invoke(song.id)
                swipeToDismissBoxState.snapTo(SwipeToDismissBoxValue.Settled)
            }

            else -> {
                // No action
            }
        }
    }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    Icon(
                        imageVector = Icons.Default.Queue,
                        contentDescription = "Play Next",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary)
                            .wrapContentSize(Alignment.CenterStart)
                            .padding(12.dp),
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }

                else -> {
                    // No action
                }
            }
        },
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
    ) {
        SongCard(
            artwork = song.artwork,
            title = song.name,
            artist = song.artist,
            index = index,
            isShowArtwork = isShowArtwork,
            sideMargin = sideMargin,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
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
                text = { Text(text = stringResource(R.string.play_dropdown_menu_play)) },
                onClick = {
                    onClickPlay.invoke(song.id)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.play_dropdown_menu_play_next)) },
                onClick = {
                    onClickPlayNext.invoke(song.id)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.play_dropdown_menu_play_later)) },
                onClick = {
                    onClickPlayLater.invoke(song.id)
                    expanded = false
                }
            )
        }
    }
}
