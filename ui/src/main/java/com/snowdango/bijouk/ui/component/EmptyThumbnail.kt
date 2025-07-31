package com.snowdango.bijouk.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.snowdango.bijouk.ui.BijouKTheme

@Composable
fun EmptyThumbnail(
    imageVector: ImageVector,
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
        contentAlignment = Alignment.Center,
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.4f)
                .aspectRatio(1f)
                .align(Alignment.Center),
        )
    }
}

@Preview
@Composable
private fun PreviewEmptyThumbnail() {
    BijouKTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            EmptyThumbnail(
                imageVector = Icons.Default.MusicNote
            )
        }
    }
}