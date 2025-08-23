package com.snowdango.bijouk.ui.component.song

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.component.EmptyThumbnail
import com.snowdango.bijouk.ui.component.LoadingThumbnail
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@Composable
fun SongCard(
    artwork: String,
    title: String,
    artist: String,
    index: Int? = null,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val indexTextSize = with(density) { (48.dp / 3).toSp() }
    val indexTextWidth = with(density) { (indexTextSize * 3).toDp() }
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (index != null) {
                Text(
                    text = index.toString(),
                    fontSize = indexTextSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(indexTextWidth)
                )
            }
            SubcomposeAsyncImage(
                model = cacheableImageRequest(
                    context = LocalContext.current,
                    data = artwork
                ).build(),
                contentDescription = null,
                loading = {
                    LoadingThumbnail()
                },
                success = {
                    Image(
                        painter = it.painter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                },
                error = {
                    EmptyThumbnail(
                        imageVector = Icons.Default.MusicNote,
                    )
                },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = artist,
                    modifier = Modifier
                        .alpha(alpha = 0.5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier
                .padding(
                    start = 48.dp + if (index != null) indexTextWidth else 0.dp
                )
        )
    }
}

@Preview
@Composable
private fun PreviewSongCard() {
    BijouKTheme {
        SongCard(
            artwork = "",
            title = "Song Title",
            artist = "Artist Name",
            index = 1,
            modifier = Modifier.padding(16.dp)
        )
    }
}
