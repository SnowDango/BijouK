package com.snowdango.bijouk.features.search.playlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchPlaylistScreen(
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    modifier: Modifier = Modifier,
    viewModel: SearchPlaylistViewModel = koinViewModel<SearchPlaylistViewModel> {
        parametersOf(baseUrl, token)
    },
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {

    }

}