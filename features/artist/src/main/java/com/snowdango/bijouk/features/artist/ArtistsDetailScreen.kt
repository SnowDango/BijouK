package com.snowdango.bijouk.features.artist

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.features.artist.component.ArtistFullAlbums
import com.snowdango.bijouk.features.artist.component.ArtistSingles
import com.snowdango.bijouk.features.artist.component.ArtistTopSongs
import com.snowdango.bijouk.features.artist.component.ArtistsDetailTopBar
import com.snowdango.bijouk.model.cider.data.ArtistDetailData
import com.snowdango.bijouk.model.cider.data.entity.Album
import com.snowdango.bijouk.model.cider.data.entity.Song
import com.snowdango.bijouk.ui.component.ProgressContent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsDetailScreen(
    baseUrl: String,
    token: String,
    artistId: String,
    sheetMinSize: Dp,
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    viewModel: ArtistsDetailViewModel = koinViewModel<ArtistsDetailViewModel> {
        parametersOf(
            baseUrl,
            token,
            artistId,
        )
    },
) {
    val context = LocalContext.current
    val uiState = viewModel.artistDetailDataFlow.collectAsStateWithLifecycle()
    val artistTopSongs = viewModel.artistTopSongsFlow.collectAsStateWithLifecycle()
    val artistFullAlbums = viewModel.artistFullAlbumsFlow.collectAsStateWithLifecycle()
    val artistSingles = viewModel.artistSinglesFlow.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.value) {
        if (uiState.value is ArtistsDetailViewModel.UiState.Error) {
            Toast.makeText(context, R.string.artist_loading_error_toast, Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when (uiState.value) {
            is ArtistsDetailViewModel.UiState.Loading -> {
                ProgressContent(
                    modifier = Modifier
                        .padding(bottom = sheetMinSize)
                )
            }

            is ArtistsDetailViewModel.UiState.Success -> {
                val detailData =
                    (uiState.value as ArtistsDetailViewModel.UiState.Success).artistDetailData
                ArtistsDetailContent(
                    detailData = detailData,
                    artistTopSongs = artistTopSongs.value,
                    artistFullAlbums = artistFullAlbums.value,
                    artistSingles = artistSingles.value,
                    sheetMinSize = sheetMinSize,
                    onNavigationBack = onNavigationBack,
                    onPlayStation = viewModel::stationPlayById,
                )
            }

            is ArtistsDetailViewModel.UiState.Error -> {
                onNavigationBack.invoke()
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsDetailContent(
    detailData: ArtistDetailData,
    artistTopSongs: List<Song>?,
    artistFullAlbums: List<Album>?,
    artistSingles: List<Album>?,
    sheetMinSize: Dp,
    onNavigationBack: () -> Unit,
    onPlayStation: (stationId: String) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Log.d("ArtistsDetailContent", "stations: ${detailData.stations}")
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArtistsDetailTopBar(
                name = detailData.name,
                artwork = detailData.artwork,
                scrollBehavior = scrollBehavior,
                onNavigationBack = onNavigationBack,
                onPlayStation = onPlayStation,
                station = detailData.stations.firstOrNull(),
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            if (!artistTopSongs.isNullOrEmpty()) {
                item {
                    ArtistTopSongs(artistTopSongs)
                }
            }
            if (!artistFullAlbums.isNullOrEmpty()) {
                item {
                    ArtistFullAlbums(artistFullAlbums)
                }
            }
            if (!artistSingles.isNullOrEmpty()) {
                item {
                    ArtistSingles(artistSingles)
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
