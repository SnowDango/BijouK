package com.snowdango.bijouk.features.artist

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
    val artistDetailData = viewModel.artistDetailDataFlow.collectAsStateWithLifecycle()
    val artistTopSongs = viewModel.artistTopSongsFlow.collectAsStateWithLifecycle()
    val artistFullAlbums = viewModel.artistFullAlbumsFlow.collectAsStateWithLifecycle()
    val artistSingles = viewModel.artistSinglesFlow.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        val detailData = artistDetailData.value
        if (detailData != null) {
            ArtistsDetailContent(
                detailData = detailData,
                artistTopSongs = artistTopSongs.value,
                artistFullAlbums = artistFullAlbums.value,
                artistSingles = artistSingles.value,
                sheetMinSize = sheetMinSize,
                onNavigationBack = onNavigationBack,
                onPlayStation = viewModel::stationPlayById,
            )
        } else {
            // TODO: Failure UI
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
                vertical = sheetMinSize,
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
        }
    }
}
