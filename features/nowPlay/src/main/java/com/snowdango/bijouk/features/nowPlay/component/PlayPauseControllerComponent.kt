package com.snowdango.bijouk.features.nowPlay.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.model2.cider.data.PlayBackTimeData

@Composable
fun PlayPauseControllerComponent(
    playBackTimeData: PlayBackTimeData?,
    onClickPlayPause: () -> Unit,
    onClickNext: () -> Unit,
    modifier: Modifier = Modifier,
    onClickPrevious: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.SkipPrevious,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onClickPrevious.invoke()
                    },
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (playBackTimeData?.isPlaying == true) {
                    Icons.Default.Pause
                } else {
                    Icons.Default.PlayArrow
                },
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onClickPlayPause.invoke()
                    }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.SkipNext,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        onClickNext.invoke()
                    },
            )
        }
    }
}
