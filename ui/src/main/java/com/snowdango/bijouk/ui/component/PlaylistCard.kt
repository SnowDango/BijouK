package com.snowdango.bijouk.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@Composable
fun PlaylistCard(
    name: String,
    editor: String,
    thumbnail: String?,
    trackThumbs: List<String>?,
    isLibrary: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SubcomposeAsyncImage(
                model = cacheableImageRequest(
                    context = LocalContext.current,
                    data = thumbnail,
                ).build(),
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
                    )
                },
                error = {
                    if (trackThumbs.isNullOrEmpty()) {
                        EmptyThumbnail(
                            imageVector = Icons.AutoMirrored.Default.QueueMusic,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    } else if (trackThumbs.size >= 4) {
                        PlaylistTracksGridThumb(
                            trackThumbs = trackThumbs,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    } else {
                        AsyncImage(
                            model = cacheableImageRequest(
                                context = LocalContext.current,
                                data = trackThumbs.firstOrNull(),
                            ).build(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                },
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = name,
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    maxLines = if (isLibrary) 2 else 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                )
                if (!isLibrary) {
                    Text(
                        text = editor,
                        modifier = Modifier
                            .alpha(alpha = 0.5f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun PlaylistTracksGridThumb(
    trackThumbs: List<String>,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val imageSize = (maxWidth - 1.dp) / 2
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
            ) {
                AsyncImage(
                    model = cacheableImageRequest(
                        context = LocalContext.current,
                        data = trackThumbs.getOrNull(0),
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                )
                Spacer(modifier = Modifier.width(1.dp))
                AsyncImage(
                    model = cacheableImageRequest(
                        context = LocalContext.current,
                        data = trackThumbs.getOrNull(1),
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                )
            }
            Row(
                modifier = Modifier
            ) {
                AsyncImage(
                    model = cacheableImageRequest(
                        context = LocalContext.current,
                        data = trackThumbs.getOrNull(2),
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                )
                Spacer(modifier = Modifier.width(1.dp))
                AsyncImage(
                    model = cacheableImageRequest(
                        context = LocalContext.current,
                        data = trackThumbs.getOrNull(3),
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAlbumCard(
    @PreviewParameter(AlbumThumbPreviewParameters::class)
    thumb: String?,
) {
    BijouKTheme {
        PlaylistCard(
            name = "Playlist Name",
            editor = "Editor Name",
            thumbnail = thumb,
            trackThumbs = listOf(),
        )
    }
}

private class AlbumThumbPreviewParameters : PreviewParameterProvider<String?> {
    override val values: Sequence<String?>
        get() = sequenceOf(null, "")
}
