package com.snowdango.bijouk.features.search.artist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.snowdango.bijouk.model.cider.data.entity.ArtistData
import com.snowdango.bijouk.ui.component.artist.ArtistCard

@Composable
fun SearchArtistsScreen(
    sheetMinSize: Dp,
    searchArtists: LazyPagingItems<ArtistData>,
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
                    Box(
                        modifier = modifier
                            .padding(all = 8.dp)
                            .fillMaxWidth()
                    ) {
                        ArtistCard(
                            name = it.name,
                            thumbnail = it.artwork,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onClickArtist.invoke(it.id)
                                }
                        )
                    }
                }
            }
        }
    }
}
