package com.snowdango.bijouk.presenter.second.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.ui.BijouKTheme

@Composable
fun PlayBackStateButtonsComponent(
    nowPlayingStatusData: NowPlayingStatusData,
    isShuffled: Boolean,
    onClickShuffle: () -> Unit,
    modifier: Modifier = Modifier
) {
    BijouKTheme {
        Row(
            modifier = modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (isShuffled) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceContainerHigh
                        }
                    )
                    .clickable {
                        onClickShuffle.invoke()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = null,
                    tint = if (isShuffled) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        LocalContentColor.current
                    },
                    modifier = Modifier
                        .size(24.dp),
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (nowPlayingStatusData.isInLib) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceContainerHigh
                        }
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (nowPlayingStatusData.isInLib) {
                        Icons.Default.Check
                    } else {
                        Icons.Default.LibraryMusic
                    },
                    contentDescription = null,
                    tint = if (nowPlayingStatusData.isInLib) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        LocalContentColor.current
                    },
                    modifier = Modifier
                        .size(24.dp),
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        if (nowPlayingStatusData.isFav) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceContainerHigh
                        }
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (nowPlayingStatusData.isFav) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = null,
                    tint = if (nowPlayingStatusData.isFav) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        LocalContentColor.current
                    },
                    modifier = Modifier
                        .size(24.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewPlayBackStateButtonsComponent(
    isIs: Boolean = true,
) {
    BijouKTheme {
        PlayBackStateButtonsComponent(
            nowPlayingStatusData = NowPlayingStatusData(
                isInLib = isIs,
                isFav = isIs
            ),
            isShuffled = isIs,
            onClickShuffle = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
