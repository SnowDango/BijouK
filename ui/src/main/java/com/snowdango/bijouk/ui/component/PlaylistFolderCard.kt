package com.snowdango.bijouk.ui.component

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.ui.BijouKTheme

@Composable
fun PlaylistFolderCard(
    name: String,
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
                    EmptyThumbnail(
                        imageVector = Icons.Default.Folder
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
private fun PreviewAlbumCard() {
    BijouKTheme {
        PlaylistFolderCard(
            name = "Folder Name",
        )
    }
}
