package com.snowdango.bijouk.features.now_play.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun QueueContent(
    modifier: Modifier = Modifier,
    sheetSize: Dp,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
    ) {
        

        item {
            Spacer(
                modifier = Modifier
                    .height(sheetSize)
                    .fillMaxWidth()
            )
        }
    }
}