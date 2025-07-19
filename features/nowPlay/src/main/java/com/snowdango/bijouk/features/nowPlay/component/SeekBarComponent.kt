package com.snowdango.bijouk.features.nowPlay.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.features.nowPlay.R
import com.snowdango.bijouk.model2.cider.data.PlayBackTimeData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeekBarComponent(playBackTimeData: PlayBackTimeData?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
    ) {
        var sliderState by remember { mutableFloatStateOf(0f) }
        var isChanging by remember { mutableStateOf(false) }
        if (!isChanging) sliderState = playBackTimeData?.currentTime ?: 0f
        Slider(
            value = sliderState,
            onValueChange = {
                isChanging = true
                sliderState = it
            },
            onValueChangeFinished = {
                isChanging = false
            },
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = remember { MutableInteractionSource() },
                    thumbSize = DpSize(0.dp, 0.dp),
                )
            },
            track = {
                SliderDefaults.Track(
                    sliderState = it,
                    thumbTrackGapSize = 0.dp,
                    modifier = Modifier
                        .height(6.dp),
                    trackInsideCornerSize = 4.dp,
                    drawStopIndicator = null,
                )
            },
            valueRange = playBackTimeData?.let { 0f..playBackTimeData.duration }
                ?: 0f..Float.MAX_VALUE,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )
        Row(
            modifier = Modifier
                .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = playBackTimeData?.currentTimeString
                    ?: stringResource(R.string.time_string_default),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
            Text(
                text = "-" + (
                        playBackTimeData?.remainingTimeString
                            ?: stringResource(R.string.time_string_default)
                        ),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    }
}
