package com.snowdango.bijouk.features.device.component

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowdango.bijouk.features.device.R
import com.snowdango.bijouk.model.devices.DeviceData
import com.snowdango.bijouk.ui.BijouKTheme

@Composable
fun DeviceCard(
    data: DeviceData,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onClickDevice: () -> Unit,
    onClickDelete: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .combinedClickable(
                onClick = {
                    onClickDevice.invoke()
                },
                onLongClick = {
                    expanded = true
                },
            ),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Computer,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    text = data.name,
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = data.baseUrl,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clip(CircleShape)
                    .size(8.dp)
                    .background(
                        if (isActive) Color.Green else Color.Red
                    )
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.device_edit_menu_delete)) },
                onClick = {
                    onClickDelete.invoke()
                    expanded = false
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview_DeviceCard() {
    BijouKTheme {
        DeviceCard(
            data = DeviceData(
                id = 1L,
                name = "古城Bijou",
                baseUrl = "http://localhost:10767",
                token = ""
            ),
            isActive = true,
            onClickDevice = {},
            onClickDelete = {}
        )
    }
}
