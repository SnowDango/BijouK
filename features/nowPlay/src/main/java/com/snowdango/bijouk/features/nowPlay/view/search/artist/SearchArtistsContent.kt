package com.snowdango.bijouk.features.nowPlay.view.search.artist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.bijouk.features.nowPlay.action.SearchArtistsAction
import com.snowdango.bijouk.features.nowPlay.component.SearchArtistCard
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchArtistsContent(
    baseUrl: String,
    token: String,
    sheetSize: Dp,
    searchArtistsAction: SearchArtistsAction,
    modifier: Modifier = Modifier,
    viewModel: SearchArtistsViewModel = koinViewModel<SearchArtistsViewModel>(
        parameters = { parametersOf(baseUrl, token) }
    ),
    onClickArtist: (artistId: String) -> Unit,
) {
    val pagingArtists = viewModel.searchArtistsFlow.collectAsLazyPagingItems()

    LaunchedEffect(searchArtistsAction) {
        viewModel.onSearchArtistsAction(searchArtistsAction)
        pagingArtists.refresh()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = sheetSize,
            )
        ) {
            items(count = pagingArtists.itemCount) { index ->
                val artist = pagingArtists[index]
                artist?.let {
                    SearchArtistCard(
                        searchArtist = it,
                        onClickArtist = onClickArtist,
                    )
                }
            }
        }
    }
}
