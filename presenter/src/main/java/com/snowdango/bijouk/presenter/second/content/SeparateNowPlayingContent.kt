package com.snowdango.bijouk.presenter.second.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.snowdango.bijouk.model.cider.data.NowPlayData
import com.snowdango.bijouk.model.cider.data.NowPlayingStatusData
import com.snowdango.bijouk.model.cider.data.PlayBackTimeData
import com.snowdango.bijouk.presenter.second.component.NowPlaySongTitleComponent
import com.snowdango.bijouk.presenter.second.component.PlayBackStateButtonsComponent
import com.snowdango.bijouk.presenter.second.component.PlayPauseControllerComponent
import com.snowdango.bijouk.presenter.second.component.SeekBarComponent
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeparateNowPlayingContent(
    name: String,
    isEnableChange: Boolean,
    nowPlayData: NowPlayData?,
    playBackTimeData: PlayBackTimeData?,
    nowPlayingStatusData: NowPlayingStatusData,
    isShuffled: Boolean,
    onPlayPause: () -> Unit,
    onSeekTo: (Float) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onClickShuffle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = name,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                ),
            )
        }
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = cacheableImageRequest(
                    context = LocalContext.current,
                    data = nowPlayData?.artwork,
                ).build(),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(fraction = 0.3f)
                    .aspectRatio(1.0f)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                contentScale = ContentScale.FillBounds,
            )

            NowPlaySongTitleComponent(
                title = nowPlayData?.name.orEmpty(),
                artist = nowPlayData?.artistName.orEmpty(),
                modifier = Modifier
                    .padding(top = 60.dp)
            )

            PlayBackStateButtonsComponent(
                nowPlayingStatusData = nowPlayingStatusData,
                isShuffled = isShuffled,
                onClickShuffle = onClickShuffle,
                modifier = Modifier.padding(top = 12.dp)
            )
            SeekBarComponent(
                isEnableChange = isEnableChange,
                playBackTimeData = playBackTimeData,
                modifier = Modifier
                    .padding(top = 20.dp),
                onMoveSeek = {
                    onSeekTo.invoke(it)
                }
            )
            PlayPauseControllerComponent(
                playBackTimeData = playBackTimeData,
                onClickPlayPause = {
                    onPlayPause.invoke()
                },
                onClickNext = {
                    onNext.invoke()
                },
                onClickPrevious = {
                    onPrevious.invoke()
                },
                modifier = Modifier
                    .padding(top = 40.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSeparateNowPlayingContent() {
    BijouKTheme {
        SeparateNowPlayingContent(
            name = "Now Playing",
            isEnableChange = true,
            nowPlayData = null,
            playBackTimeData = null,
            nowPlayingStatusData = NowPlayingStatusData(isFav = false, isInLib = false),
            isShuffled = true,
            onPlayPause = {},
            onSeekTo = {},
            onNext = {},
            onPrevious = {},
            onClickShuffle = {},
        )
    }
}
