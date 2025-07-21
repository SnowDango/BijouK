package com.snowdango.bijouk.features.artist.component

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.snowdango.bijouk.ui.BijouKTheme

@Composable
fun ArtistsDetailHeader(
    name: String,
    artwork: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(artwork)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .aspectRatio(1.0f)
                .clip(shape = CircleShape)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(fraction = 0.75f)
                .basicMarquee()
        )
    }
}

@Preview
@Composable
private fun PreviewArtistsDetailHeader() {
    BijouKTheme {
        ArtistsDetailHeader(
            name = "Artist Name",
            artwork = "https://example.com/artwork.jpg",
        )
    }
}
