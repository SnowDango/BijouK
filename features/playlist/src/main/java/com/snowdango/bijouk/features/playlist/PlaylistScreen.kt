package com.snowdango.bijouk.features.playlist

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.snowdango.bijouk.model.cider.data.entity.PlaylistData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import com.snowdango.bijouk.ui.component.EmptyThumbnail
import com.snowdango.bijouk.ui.component.LoadingThumbnail
import com.snowdango.bijouk.ui.component.ProgressContent
import com.snowdango.bijouk.ui.component.TitleTopBar
import com.snowdango.bijouk.ui.component.playlist.PlaylistTracksGridThumb
import com.snowdango.bijouk.ui.image.cacheableImageRequest
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
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TitleTopBar(
                title = playlistDetail.name,
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
                        .padding(bottom = 48.dp)
                        .basicMarquee(),
                )
            }

            items(count = playlistSongs.itemCount) { index ->
                val song = playlistSongs[index]
                song?.let {
                    // TODO: Implement song item UI
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