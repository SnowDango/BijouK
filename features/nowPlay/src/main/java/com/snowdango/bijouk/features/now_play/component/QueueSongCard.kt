package com.snowdango.bijouk.features.now_play.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.snowdango.bijouk.model.cider.data.QueueData
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.component.SongCard
import java.nio.file.WatchEvent

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
    ){
        SongCard(
            artwork = queueData.artwork,
            title = queueData.name,
            artist = queueData.artist,
            modifier = Modifier.fillMaxWidth()
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
                text = { Text("次に再生") },
                onClick = { onClickNext.invoke() }
            )
            DropdownMenuItem(
                text = { Text("ここまでスキップ") },
                onClick = { onClickSkip.invoke() }
            )
        }
    }
}

@Preview
@Composable
private fun Preview_QueueSongCard() {
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
