package com.snowdango.bijouk.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.snowdango.bijouk.ui.BijouKTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTopBar(
    title: String,
    titleColor: Color? = null,
    navigationIcon: ImageVector? = null,
    navigationOnClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                color = titleColor ?: Color.Unspecified,
                style = MaterialTheme.typography.headlineMedium,
            )
        },
        navigationIcon = {
            navigationIcon?.let {
                IconButton(
                    onClick = {
                        navigationOnClick.invoke()
                    }
                ) {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        )
    )
}

@Preview
@Composable
fun PreviewTitleTopBar() {
    BijouKTheme {
        TitleTopBar(
            title = "Title",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
        )
    }
}
