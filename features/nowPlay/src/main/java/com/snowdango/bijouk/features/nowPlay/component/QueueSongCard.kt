package com.snowdango.bijouk.features.nowPlay.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.features.nowPlay.R
import com.snowdango.bijouk.model.cider.data.QueueData
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.component.SongCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QueueSongCard(
    queueData: QueueData,
    onClickNext: () -> Unit,
    modifier: Modifier = Modifier,
    onClickSkip: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
    ) {
        SongCard(
            artwork = queueData.artwork,
            title = queueData.name,
            artist = queueData.artist,
            modifier = Modifier
                .fillMaxWidth()
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
                text = { Text(text = stringResource(R.string.queue_dropdown_menu_next)) },
                onClick = { onClickNext.invoke() }
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.queue_dropdown_menu_skip)) },
                onClick = { onClickSkip.invoke() }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewQueueSongCard() {
    BijouKTheme {
        QueueSongCard(
            queueData = QueueData(
                songId = "",
                name = "name",
                artist = "artist",
                artwork = "",
                album = "",
                state = QueueData.State.Waiting,
            ),
            onClickNext = {},
            onClickSkip = {},
        )
    }
}
