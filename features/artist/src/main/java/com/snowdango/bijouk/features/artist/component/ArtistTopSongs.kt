package com.snowdango.bijouk.features.artist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
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
import com.snowdango.bijouk.model.cider.data.entity.Song
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@Composable
fun ArtistTopSongs(
    songs: List<Song>,
    modifier: Modifier = Modifier,
) {
    @Suppress("MagicNumber")
    val topSongsCell = 4

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
                text = "Top Songs",
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
            val chunkedSongs = songs.chunked(topSongsCell)
            chunkedSongs.forEach { chunk ->
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxWidth(),
                    ) {
                        chunk.forEachIndexed { index, song ->
                            TopSongs(
                                song = song,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            )
                            if (chunk.getOrNull(index + 1) != null) {
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(start = 56.dp, end = 16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopSongs(
    song: Song,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(end = 16.dp, top = 8.dp, bottom = 4.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = cacheableImageRequest(
                context = LocalContext.current,
                data = song.artwork
            ).build(),
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp)),
        )
        Box {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = song.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = song.artist,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(alpha = 0.5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTopSong() {
    BijouKTheme {
        TopSongs(
            song = Song(
                id = "1",
                name = "Top Song",
                artist = "Artist Name",
                album = "Album Name",
                artwork = "https://example.com/artwork.jpg",
                href = "https://example.com/song.mp3",
                genres = listOf("Pops"),
                hasLyrics = true,
                composerName = "Composer Name",
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewArtistTopSongs() {
    BijouKTheme {
        ArtistTopSongs(
            songs = listOf(
                Song(
                    id = "1",
                    name = "Top Song 1",
                    artist = "Artist Name",
                    album = "Album Name",
                    artwork = "https://example.com/artwork1.jpg",
                    href = "https://example.com/song1.mp3",
                    genres = listOf("Pops"),
                    hasLyrics = true,
                    composerName = "Composer Name",
                ),
                Song(
                    id = "2",
                    name = "Top Song 2",
                    artist = "Artist Name",
                    album = "Album Name",
                    artwork = "https://example.com/artwork2.jpg",
                    href = "https://example.com/song2.mp3",
                    genres = listOf("Rock"),
                    hasLyrics = true,
                    composerName = "Composer Name",
                )
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
