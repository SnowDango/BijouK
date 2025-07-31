package com.snowdango.bijouk.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@Composable
fun ArtistCard(
    name: String,
    thumbnail: String,
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
                ) {

                    SubcomposeAsyncImage(
                        model = cacheableImageRequest(
                            context = LocalContext.current,
                            data = thumbnail,
                        ).build(),
                        contentScale = ContentScale.Inside,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = CircleShape),
                        loading = {
                            LoadingThumbnail()
                        },
                        success = {
                            Image(
                                painter = it.painter,
                                contentDescription = null,
                                contentScale = ContentScale.Inside,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(shape = CircleShape),
                            )
                        },
                        error = {
                            EmptyThumbnail(
                                imageVector = Icons.Default.Person,
                            )
                        }
                    )
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
            }
        }
    }
}

@Preview
@Composable
private fun PreviewArtistCard() {
    BijouKTheme {
        ArtistCard(
            name = "Artist Name",
            thumbnail = "https://example.com/artist_thumbnail.jpg",
        )
    }
}
