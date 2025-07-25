package com.snowdango.bijouk.features.device.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.snowdango.bijouk.ui.BijouKTheme

@Composable
fun AddDeviceDialog(
    isTestActive: Boolean?,
    onDismissRequest: () -> Unit,
    onChange: () -> Unit,
    onClickTest: (host: String, port: Int?, token: String, isUseSsl: Boolean) -> Unit,
    onClickAdd: (name: String, host: String, port: Int?, token: String, isUseSsl: Boolean) -> Unit,
) {
    var isValidateError: String? by remember { mutableStateOf(null) }

    var name by remember { mutableStateOf("") }
    var host by remember { mutableStateOf("") }
    var port by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var isUseSsl by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(text = "キャンセル")
            }
        },
        confirmButton = {
            Row {
                TextButton(
                    onClick = {
                        onClickTest.invoke(host, port.toIntOrNull(), token, isUseSsl)
                    }
                ) {
                    Text(text = "Test")
                }
                TextButton(
                    onClick = {
                        if (name.isBlank()) {
                            isValidateError = "Nameが指定されていません"
                        } else if (host.isBlank()) {
                            isValidateError = "Hostが指定されていません"
                        } else if (token.isBlank()) {
                            isValidateError = "Tokenが指定されていません"
                        } else {
                            isValidateError = null
                            onClickAdd.invoke(name, host, port.toIntOrNull(), token, isUseSsl)
                            onDismissRequest.invoke()
                        }
                    }
                ) {
                    Text(text = "追加")
                }
            }
        },
        title = {
            Text(text = "デバイスを追加")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = {
                        Text(text = "Name")
                    },
                    placeholder = {
                        Text(text = "Bijou")
                    }
                )
                OutlinedTextField(
                    value = host,
                    onValueChange = {
                        host = it
                        onChange.invoke()
                    },
                    label = {
                        Text(text = "Host")
                    },
                    placeholder = {
                        Text(text = "ex.) 192.168.1.1")
                    }
                )
                OutlinedTextField(
                    value = port,
                    onValueChange = {
                        if (it.isDigitsOnly()) {
                            port = it
                            onChange.invoke()
                        }
                    },
                    label = {
                        Text(text = "Port")
                    },
                    placeholder = {
                        Text(text = "10767")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    ),
                )
                OutlinedTextField(
                    value = token,
                    onValueChange = {
                        token = it
                        onChange.invoke()
                    },
                    label = {
                        Text(text = "Token")
                    },
                    placeholder = {
                        Text(text = "generate Cider")
                    },
                )
                Row(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .width(OutlinedTextFieldDefaults.MinWidth),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Use SSL",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    )
                    Checkbox(
                        checked = isUseSsl,
                        onCheckedChange = {
                            isUseSsl = it
                            onChange.invoke()
                        }
                    )
                }
                isTestActive?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = if (it) {
                                Icons.Default.CheckCircle
                            } else {
                                Icons.Default.Error
                            },
                            tint = if (it) {
                                Color.Green
                            } else {
                                Color.Red
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp),
                        )
                        Text(
                            text = if (isTestActive) {
                                "Success connected"
                            } else {
                                "Failed connected"
                            },
                            color = if (isTestActive) {
                                Color.Green
                            } else {
                                Color.Red
                            },
                            modifier = Modifier
                                .padding(start = 16.dp),
                        )
                    }
                }
                isValidateError?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            tint = Color.Red,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp),
                        )
                        Text(
                            text = it,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(start = 16.dp),
                        )
                    }
                }
            }
        },
    )
}

@Preview
@Composable
private fun Preview_AddDeviceDialog() {
    BijouKTheme {
        AddDeviceDialog(
            isTestActive = false,
            onDismissRequest = {},
            onChange = {},
            onClickAdd = { _, _, _, _, _ -> },
            onClickTest = { _, _, _, _ -> }
        )
    }
}
