package com.snowdango.bijouk.features.playlist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.model.mock.mockPlaylistData
import com.snowdango.bijouk.model.mock.mockSongData
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.component.EmptyThumbnail
import com.snowdango.bijouk.ui.component.LoadingThumbnail
import com.snowdango.bijouk.ui.component.ProgressContent
import com.snowdango.bijouk.ui.component.TitleTopBar
import com.snowdango.bijouk.ui.component.playlist.PlaylistTracksGridThumb
import com.snowdango.bijouk.ui.component.song.PlayableSongCard
import com.snowdango.bijouk.ui.image.cacheableImageRequest
import kotlinx.coroutines.flow.flowOf
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PlaylistScreen(
    baseUrl: String,
    token: String,
    playlistId: String,
    isLibrary: Boolean,
    sheetMinSize: Dp,
    onNavigationBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel = koinViewModel<PlaylistViewModel> {
        parametersOf(baseUrl, token, playlistId, isLibrary)
    }
) {
    val context = LocalContext.current
    val uiState = viewModel.playlistDetailState.collectAsStateWithLifecycle()
    val playlistSongs = viewModel.playlistSongs.collectAsLazyPagingItems()

    LaunchedEffect(uiState.value) {
        if (uiState.value is PlaylistViewModel.UiState.Error) {
            Toast.makeText(context, R.string.playlist_loading_error_toast, Toast.LENGTH_SHORT)
                .show()
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState.value) {
            is PlaylistViewModel.UiState.Loading -> {
                ProgressContent(
                    modifier = Modifier
                        .padding(bottom = sheetMinSize)
                )
            }

            is PlaylistViewModel.UiState.Success -> {
                val playlistDetail =
                    (uiState.value as PlaylistViewModel.UiState.Success).playlistData
                PlaylistDetailContent(
                    playlistDetail = playlistDetail,
                    playlistSongs = playlistSongs,
                    sheetMinSize = sheetMinSize,
                    onNavigationBack = onNavigationBack,
                    onSongPlay = viewModel::songPlay,
                    onSongPlayNext = viewModel::songPlayNext,
                    onSongPlayLater = viewModel::songPlayLater,
                    onPlaylistPlay = viewModel::playlistPlay,
                    onPlaylistPlayShuffled = viewModel::playlistPlayShuffled,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is PlaylistViewModel.UiState.Error -> {
                onNavigationBack.invoke()
            }
        }
    }
}

@Composable
fun PlaylistDetailContent(
    playlistDetail: PlaylistData,
    playlistSongs: LazyPagingItems<SongData>,
    sheetMinSize: Dp,
    onNavigationBack: () -> Unit,
    onSongPlay: (String) -> Unit,
    onSongPlayNext: (String) -> Unit,
    onSongPlayLater: (String) -> Unit,
    onPlaylistPlay: (String) -> Unit,
    onPlaylistPlayShuffled: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TitleTopBar(
                title = playlistDetail.name,
                navigationIcon = Icons.AutoMirrored.Default.ArrowBack,
                navigationOnClick = onNavigationBack,
            )
        },
    ) { paddingValues ->
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(
                top = 32.dp,
                start = 16.dp,
                end = 16.dp,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            item {
                @Suppress("MagicNumber")
                PlaylistThumbnail(
                    artwork = playlistDetail.artwork,
                    songThumbs = playlistSongs.itemSnapshotList.items.filter {
                        it.artwork.isNotBlank()
                    }.take(4).map { it.artwork },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 28.dp)
                )
            }

            item {
                Text(
                    text = playlistDetail.name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .padding(bottom = 8.dp)
                        .basicMarquee(),
                )
            }

            item {
                Text(
                    text = playlistDetail.curatorName,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .padding(bottom = 16.dp)
                        .basicMarquee(),
                )
            }

            item {
                PlaylistPlayButton(
                    playlistId = playlistDetail.id,
                    onPlayPlaylist = onPlaylistPlay,
                    onPlayPlaylistShuffle = onPlaylistPlayShuffled,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
            }

            item {
                Text(
                    text = "Songs",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                )
            }

            items(count = playlistSongs.itemCount) { index ->
                val song = playlistSongs[index]
                song?.let {
                    PlayableSongCard(
                        song = it,
                        onClickPlay = onSongPlay,
                        onClickPlayNext = onSongPlayNext,
                        onClickPlayLater = onSongPlayLater,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            if (playlistSongs.loadState.append.endOfPaginationReached) {
                item {
                    Text(
                        text = "${playlistSongs.itemCount} songs",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .alpha(0.5f)
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
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
fun PlaylistThumbnail(
    artwork: String?,
    songThumbs: List<String>,
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
                if (songThumbs.isEmpty()) {
                    EmptyThumbnail(
                        imageVector = Icons.AutoMirrored.Default.QueueMusic,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else if (songThumbs.size >= 4 && songThumbs.distinct().size > 1) {
                    PlaylistTracksGridThumb(
                        trackThumbs = songThumbs,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else {
                    AsyncImage(
                        model = cacheableImageRequest(
                            context = LocalContext.current,
                            data = songThumbs.firstOrNull(),
                        ).build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
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

@Composable
fun PlaylistPlayButton(
    playlistId: String,
    onPlayPlaylist: (String) -> Unit,
    onPlayPlaylistShuffle: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextButton(
            onClick = {
                onPlayPlaylist.invoke(playlistId)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .weight(1f)
        ) {
            val playIcon = "playIcon"
            Text(
                text = buildAnnotatedString {
                    appendInlineContent(playIcon)
                    append("Play")
                },
                inlineContent = mapOf(
                    playIcon to InlineTextContent(
                        placeholder = Placeholder(
                            width = 18.sp,
                            height = 18.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
                        ),
                        children = {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                            )
                        },
                    ),
                ),
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        TextButton(
            onClick = {
                onPlayPlaylistShuffle.invoke(playlistId)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .weight(1f)
        ) {
            val shuffleIcon = "shuffleIcon"
            Text(
                text = buildAnnotatedString {
                    appendInlineContent(shuffleIcon)
                    append("Shuffle")
                },
                inlineContent = mapOf(
                    shuffleIcon to InlineTextContent(
                        placeholder = Placeholder(
                            width = 18.sp,
                            height = 18.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
                        ),
                        children = {
                            Icon(
                                imageVector = Icons.Default.Shuffle,
                                contentDescription = null,
                            )
                        },
                    ),
                ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewPlaylistDetailContent() {
    val playlistSongs = flowOf(PagingData.from(listOf(mockSongData))).collectAsLazyPagingItems()

    BijouKTheme {
        PlaylistDetailContent(
            playlistDetail = mockPlaylistData,
            playlistSongs = playlistSongs,
            sheetMinSize = 0.dp,
            onNavigationBack = {},
            onSongPlay = {},
            onSongPlayNext = {},
            onSongPlayLater = {},
            onPlaylistPlay = {},
            onPlaylistPlayShuffled = {},
        )
    }
}
