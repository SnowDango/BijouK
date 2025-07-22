package com.snowdango.bijouk.features.artist.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.snowdango.bijouk.ui.BijouKTheme

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsDetailTopBar(
    name: String,
    artwork: String,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {

    val density = LocalDensity.current
    val systemBarHeight = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    var artworkAlpha by remember { mutableFloatStateOf(1.0f) }
    var titleBlurHeight by remember { mutableStateOf(0.dp) }

    LaunchedEffect(scrollBehavior.state.heightOffset) {
        if (scrollBehavior.state.heightOffset == scrollBehavior.state.heightOffsetLimit) {
            if (artworkAlpha != 0.0f) artworkAlpha = 0.0f
        } else {
            if (artworkAlpha != 1.0f) artworkAlpha = 1.0f
        }
    }

    BoxWithConstraints(
        modifier = modifier
    ) {
        val width = maxWidth
        ConstraintLayout(
            modifier = modifier,
            constraintSet = constraintSet,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(artwork)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .width(width)
                    .height(width)
                    .layoutId("artwork")
                    .alpha(artworkAlpha),
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(titleBlurHeight + 32.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.2f),
                            )
                        )
                    )
                    .layoutId("titleBlur"),
            )

            LargeTopAppBar(
                title = {
                    Text(
                        text = name,
                        maxLines = 2,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier
                            .onGloballyPositioned {
                                with(density) {
                                    titleBlurHeight = it.size.height.toDp()
                                }
                            }
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
                expandedHeight = width - systemBarHeight,
                scrollBehavior = scrollBehavior,
                modifier = Modifier
                    .layoutId("topbar")
            )
        }
    }
}

val constraintSet = ConstraintSet {
    val artworkRef = createRefFor("artwork")
    val topbarRef = createRefFor("topbar")
    val titleBlurRef = createRefFor("titleBlur")
    constrain(artworkRef) {
        start.linkTo(topbarRef.start)
        end.linkTo(topbarRef.end)
        bottom.linkTo(topbarRef.bottom)
    }
    constrain(titleBlurRef) {
        start.linkTo(artworkRef.start)
        end.linkTo(artworkRef.end)
        bottom.linkTo(parent.bottom)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewArtistsDetailHeader() {
    BijouKTheme {
        ArtistsDetailTopBar(
            name = "Artist Name",
            artwork = "https://example.com/artwork.jpg",
            scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        )
    }
}
