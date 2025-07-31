package com.snowdango.bijouk.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@Composable
fun PlaylistCard(
    name: String,
    editor: String,
    thumbnail: String?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                horizontalAlignment = CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .aspectRatio(1.0f)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    if (thumbnail != null) {
                        AsyncImage(
                            model = cacheableImageRequest(
                                context = LocalContext.current,
                                data = thumbnail,
                            ).build(),
                            contentScale = ContentScale.Inside,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    } else {
                        EmptyPlaylistThumb(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(fraction = 0.7f)
                        .basicMarquee()
                )
                Text(
                    text = editor,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .alpha(0.5f)
                        .fillMaxWidth(fraction = 0.7f)
                        .basicMarquee()
                )
            }
        }
    }
}

@Composable
fun EmptyPlaylistThumb(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.White,
                        MaterialTheme.colorScheme.primary,
                    )
                ),
            ),
        contentAlignment = Alignment.TopStart,
    ) {
    }
}

@Preview
@Composable
private fun PreviewPlaylistCardEmpty() {
    BijouKTheme {
        Box(
            modifier = Modifier
                .aspectRatio(1.0f)
        ) {
            EmptyPlaylistThumb()
        }
    }
}

@Preview
@Composable
private fun PreviewAlbumCard() {
    BijouKTheme {
        PlaylistCard(
            name = "Playlist Name",
            editor = "Editor Name",
            thumbnail = "",
        )
    }
}
