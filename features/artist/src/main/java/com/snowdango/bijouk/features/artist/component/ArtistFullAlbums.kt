package com.snowdango.bijouk.features.artist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.snowdango.bijouk.model.cider.data.entity.Album
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@Composable
fun ArtistFullAlbums(
    albums: List<Album>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(vertical = 16.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Albums",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
            )
            Image(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
        LazyRow(
            contentPadding = PaddingValues(
                start = 32.dp,
                end = 16.dp,
            ),
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
        ) {
            items(albums) { album ->
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth(fraction = 0.5f)
                ) {
                    FullAlbums(
                        album = album,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    )
                }
            }
        }
    }
}

@Composable
fun FullAlbums(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 8.dp, end = 8.dp, bottom = 4.dp)
    ) {
        AsyncImage(
            model = cacheableImageRequest(
                context = LocalContext.current,
                data = album.artwork,
            ).build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = album.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        )
        Text(
            text = album.releaseYear,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(top = 4.dp)
                .alpha(0.5f)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewFullAlbums() {
    BijouKTheme {
        FullAlbums(
            album = Album(
                id = "1",
                name = "Test Album",
                artist = "Test Artist",
                artwork = "https://example.com/artwork.jpg",
                href = "",
                genres = listOf("Pop", "Rock"),
                copyRight = "© 2023 Test Artist",
                trackCount = 20,
                releaseYear = "2023",

            ),
            modifier = Modifier.fillMaxWidth(
                fraction = 0.5f
            )
        )
    }
}
