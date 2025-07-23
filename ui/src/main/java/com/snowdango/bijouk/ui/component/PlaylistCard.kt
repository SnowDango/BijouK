package com.snowdango.bijouk.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@Composable
fun PlaylistCard(
    name: String,
    editor: String,
    thumbnail: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally,
        ) {
            AsyncImage(
                model = cacheableImageRequest(
                    context = LocalContext.current,
                    data = thumbnail,
                ).build(),
                contentDescription = null,
                modifier = Modifier
                    .background(Color.Red)
                    .aspectRatio(1.0f)
                    .fillMaxWidth(fraction = 0.5f)
                    .weight(1f)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Text(
                text = editor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .alpha(0.5f)
            )
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
