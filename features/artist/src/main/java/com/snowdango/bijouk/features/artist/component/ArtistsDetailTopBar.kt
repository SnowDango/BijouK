package com.snowdango.bijouk.features.artist.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil3.compose.AsyncImage
import com.snowdango.bijouk.features.artist.R
import com.snowdango.bijouk.model.cider.data.entity.StationData
import com.snowdango.bijouk.ui.BijouKTheme
import com.snowdango.bijouk.ui.image.cacheableImageRequest

@SuppressLint("UnusedBoxWithConstraintsScope", "AutoboxingStateCreation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsDetailTopBar(
    name: String,
    artwork: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigationBack: () -> Unit,
    onPlayStation: (stationId: String) -> Unit,
    modifier: Modifier = Modifier,
    station: StationData? = null,
) {
    val density = LocalDensity.current
    val systemBarHeight = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    var isCollapsed by remember { mutableStateOf(false) }
    var titleBlurHeight by remember { mutableStateOf(0.dp) }
    var stationAlpha by remember { mutableFloatStateOf(1.0f) }

    LaunchedEffect(scrollBehavior.state.heightOffset) {
        if (scrollBehavior.state.heightOffset == scrollBehavior.state.heightOffsetLimit) {
            if (!isCollapsed) isCollapsed = true
        } else {
            if (isCollapsed) isCollapsed = false
        }
        val stationVisibleOffsetLimit = scrollBehavior.state.heightOffsetLimit / 2
        val betweenOffset = stationVisibleOffsetLimit - scrollBehavior.state.heightOffset
        stationAlpha = if (betweenOffset >= 0f) {
            0.0f
        } else {
            betweenOffset / stationVisibleOffsetLimit
        }
    }

    BoxWithConstraints(
        modifier = modifier
    ) {
        val width = maxWidth
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth(),
            constraintSet = constraintSet,
        ) {
            AsyncImage(
                model = cacheableImageRequest(
                    context = LocalContext.current,
                    data = artwork,
                ).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(width)
                    .height(width)
                    .layoutId("artwork")
                    .alpha(if (isCollapsed) 0.0f else 1f),
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = name,
                            maxLines = if (isCollapsed) 1 else 2,
                            fontWeight = FontWeight.ExtraBold,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .onGloballyPositioned {
                                    with(density) {
                                        titleBlurHeight = it.size.height.toDp()
                                    }
                                }
                        )
                        if (station != null) {
                            Box(
                                modifier = Modifier
                                    .alpha(stationAlpha)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(end = 32.dp, bottom = 16.dp)
                                        .size(52.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
                                        .clip(shape = CircleShape)
                                        .layoutId("station")
                                        .clickable {
                                            onPlayStation.invoke(station.id)
                                        },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.icon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(44.dp),
                                    )
                                }
                            }
                        }
                    }
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable {
                                onNavigationBack.invoke()
                            },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = if (isCollapsed) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                Color.White
                            },
                            modifier = Modifier
                                .padding(all = 8.dp),
                        )
                    }
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
            artwork = "https://is1-ssl.mzstatic.com/image/thumb/Features125/v4/5e/e1/5e" +
                "/5ee15e83-fd49-3717-0a5f-4ff5b6fc619f/mzl.eszoutnf.jpg/3000x3000AM.RSAB02.jpg",
            scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
            onNavigationBack = {},
            onPlayStation = {},
            station = StationData(
                id = "station1",
                name = "Station Name",
                artwork = "https://is1-ssl.mzstatic.com/image/thumb/Features125/v4/5e/e1/5e" +
                    "/5ee15e83-fd49-3717-0a5f-4ff5b6fc619f/mzl.eszoutnf.jpg/3000x3000AM.RSAB02.jpg",
                href = "",
            )
        )
    }
}
