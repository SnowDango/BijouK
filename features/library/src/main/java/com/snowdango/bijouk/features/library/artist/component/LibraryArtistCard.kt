package com.snowdango.bijouk.features.library.artist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.model.cider.data.SearchArtist
import com.snowdango.bijouk.ui.component.ArtistCard

@Composable
fun LibraryArtistCard(
    searchArtist: SearchArtist,
    modifier: Modifier = Modifier,
    onClickArtist: (artistId: String) -> Unit,
) {
    Box(
        modifier = modifier
            .padding(all = 8.dp)
            .fillMaxWidth()
    ) {
        ArtistCard(
            name = searchArtist.name,
            thumbnail = searchArtist.artwork,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClickArtist.invoke(searchArtist.id)
                }
        )
    }
}
