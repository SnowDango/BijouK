package com.snowdango.bijouk.features.artist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowdango.bijouk.features.artist.component.ArtistTopSongs
import com.snowdango.bijouk.features.artist.component.ArtistsDetailHeader
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArtistsDetailScreen(
    baseUrl: String,
    token: String,
    artistId: String,
    modifier: Modifier = Modifier,
    viewModel: ArtistsDetailViewModel = koinViewModel<ArtistsDetailViewModel> {
        parametersOf(
            baseUrl,
            token,
            artistId,
        )
    }
) {

    val artistDetailData = viewModel.artistDetailDataFlow.collectAsStateWithLifecycle()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { paddingValues ->
            val data = artistDetailData.value
            if (data != null) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 32.dp,
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                ) {
                    item {
                        ArtistsDetailHeader(
                            data.name,
                            data.artwork,
                        )
                    }

                    data.topSongs?.let {
                        item {
                            ArtistTopSongs(it)
                        }
                    }
                }
            }
        }
    }

}