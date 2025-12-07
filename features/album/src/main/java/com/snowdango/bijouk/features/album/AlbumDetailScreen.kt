package com.snowdango.bijouk.features.album

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.SubcomposeAsyncImage
import com.snowdango.bijouk.model.cider.data.AlbumDetailData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.model.mock.mockAlbumDetailData
import com.snowdango.bijouk.model.mock.mockSongData
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.component.EmptyThumbnail
import com.snowdango.bijouk.ui.component.LoadingThumbnail
import com.snowdango.bijouk.ui.component.ProgressContent
import com.snowdango.bijouk.ui.component.TitleTopBar
import com.snowdango.bijouk.ui.component.song.PlayableSongCard
import com.snowdango.bijouk.ui.image.cacheableImageRequest
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import com.snowdango.bijouk.ui.R as UiRes

@Composable
fun AlbumDetailScreen(
    baseUrl: String,
    token: String,
    albumId: String,
    isLibrary: Boolean,
    sheetMinSize: Dp,
    onNavigationBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlbumDetailViewModel = koinViewModel<AlbumDetailViewModel> {
        parametersOf(baseUrl, token, albumId, isLibrary)
    },
) {
    val context = LocalContext.current
    val uiState = viewModel.albumDetailDataFlow.collectAsStateWithLifecycle()
    val albumSongs = viewModel.albumSongsFlow.collectAsLazyPagingItems()
    val actionResult = viewModel.actionResultFlow.collectAsStateWithLifecycle(
        initialValue = AlbumDetailViewModel.SongAction.None,
    )

    LaunchedEffect(uiState.value) {
        if (uiState.value is AlbumDetailViewModel.UiState.Error) {
            Toast.makeText(context, R.string.album_loading_error_toast, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(actionResult.value) {
        when (actionResult.value) {
            AlbumDetailViewModel.SongAction.Play -> {
                Toast.makeText(context, UiRes.string.song_action_toast_play, Toast.LENGTH_SHORT)
                    .show()
                viewModel.clearActionResult()
            }

            AlbumDetailViewModel.SongAction.PlayNext -> {
                Toast.makeText(
                    context,
                    UiRes.string.song_action_toast_play_next,
                    Toast.LENGTH_SHORT
                )
                    .show()
                viewModel.clearActionResult()
            }

            AlbumDetailViewModel.SongAction.PlayLater -> {
                Toast.makeText(
                    context,
                    UiRes.string.song_action_toast_play_later,
                    Toast.LENGTH_SHORT
                )
                    .show()
                viewModel.clearActionResult()
            }

            AlbumDetailViewModel.SongAction.None -> {
                // No action
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState.value) {
            is AlbumDetailViewModel.UiState.Loading -> {
                ProgressContent(
                    modifier = Modifier
                        .padding(sheetMinSize)
                )
            }

            is AlbumDetailViewModel.UiState.Success -> {
                val albumDetailData =
                    (uiState.value as AlbumDetailViewModel.UiState.Success).albumDetailData
                AlbumDetailContent(
                    albumDetailData = albumDetailData,
                    albumSongs = albumSongs,
                    sheetMinSize = sheetMinSize,
                    onNavigationBack = onNavigationBack,
                    onSongPlay = { songId ->
                        viewModel.songPlay(songId)
                    },
                    onSongPlayNext = { songId ->
                        viewModel.songPlayNext(songId)
                    },
                    onSongPlayLater = { songId ->
                        viewModel.songPlayLater(songId)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            is AlbumDetailViewModel.UiState.Error -> {
                onNavigationBack.invoke()
            }
        }
    }
}

@Composable
fun AlbumDetailContent(
    albumDetailData: AlbumDetailData,
    albumSongs: LazyPagingItems<SongData>,
    sheetMinSize: Dp,
    onNavigationBack: () -> Unit,
    onSongPlay: (String) -> Unit,
    onSongPlayNext: (String) -> Unit,
    onSongPlayLater: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TitleTopBar(
                title = albumDetailData.title,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationOnClick = onNavigationBack,
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = 32.dp,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                AlbumDetailThumbnail(
                    artwork = albumDetailData.artwork,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 28.dp
                        )
                )
            }

            item {
                Text(
                    text = albumDetailData.title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp,
                        )
                        .basicMarquee(),
                )
            }

            item {
                Text(
                    text = albumDetailData.artist,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp,
                        )
                        .basicMarquee(),
                )
            }

            item {
                Text(
                    text = albumDetailData.releaseDate,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                        .alpha(0.5f),
                )
            }
            items(count = albumSongs.itemCount) { index ->
                val song = albumSongs[index]
                song?.let {
                    PlayableSongCard(
                        song = it,
                        index = index + 1,
                        isShowArtwork = false,
                        onClickPlay = onSongPlay,
                        onClickPlayNext = onSongPlayNext,
                        onClickPlayLater = onSongPlayLater,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(sheetMinSize)
                )
            }
        }
    }
}

@Composable
fun AlbumDetailThumbnail(
    artwork: String?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        SubcomposeAsyncImage(
            model = cacheableImageRequest(
                context = LocalContext.current,
                data = artwork,
            ).build(),
            loading = {
                LoadingThumbnail()
            },
            success = {
                Image(
                    painter = it.painter,
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxSize()
                )
            },
            error = {
                EmptyThumbnail(
                    imageVector = Icons.AutoMirrored.Default.QueueMusic,
                    modifier = Modifier
                        .fillMaxSize()
                )
            },
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Preview
@Composable
fun PreviewAlbumDetailContent() {
    val albumSongs = flowOf(PagingData.from(listOf(mockSongData))).collectAsLazyPagingItems()

    BijouKTheme {
        AlbumDetailContent(
            albumDetailData = mockAlbumDetailData,
            albumSongs = albumSongs,
            sheetMinSize = 0.dp,
            onNavigationBack = {},
            onSongPlay = {},
            onSongPlayNext = {},
            onSongPlayLater = {},
        )
    }
}
