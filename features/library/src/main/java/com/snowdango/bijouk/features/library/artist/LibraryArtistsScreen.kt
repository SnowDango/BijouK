package com.snowdango.bijouk.features.library.artist

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.snowdango.bijouk.features.library.artist.component.LibraryArtistCard
import com.snowdango.bijouk.model.cider.data.SearchArtist

@Composable
fun LibraryArtistsScreen(
    sheetMinSize: Dp,
    searchArtists: LazyPagingItems<SearchArtist>,
    modifier: Modifier = Modifier,
    onClickArtist: (artistId: String) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(
                if (maxWidth < 600.dp) 2 else 3
            ),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = sheetMinSize,
            )
        ) {
            items(count = searchArtists.itemCount) { index ->
                val artist = searchArtists[index]
                artist?.let {
                    LibraryArtistCard(
                        searchArtist = it,
                        onClickArtist = onClickArtist,
                    )
                }
            }
        }
    }
}
