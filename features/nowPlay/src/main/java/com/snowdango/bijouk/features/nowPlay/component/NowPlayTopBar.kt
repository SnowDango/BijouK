package com.snowdango.bijouk.features.nowPlay.component

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NowPlayTopBar(
    name: String,
    onSearch: (query: String) -> Unit,
    modifier: Modifier = Modifier,
    onClearQuery: () -> Unit
) {
    var isSearch by remember { mutableStateOf(false) }
    var inputString by remember { mutableStateOf("") }

    Crossfade(
        targetState = isSearch,
        modifier = Modifier.animateContentSize()
    ) { target ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerHigh),
            contentAlignment = Alignment.Center,
        ) {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = inputString,
                        onQueryChange = { inputString = it },
                        onSearch = {
                            if (it.isBlank()) {
                                isSearch = false
                                onClearQuery.invoke()
                            } else {
                                onSearch.invoke(inputString)
                            }
                        },
                        expanded = false,
                        onExpandedChange = { },
                        placeholder = { Text("Search") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier.onFocusChanged {
                            Log.d("Focus", it.toString())
                        }
                    )
                },
                expanded = false,
                onExpandedChange = { },
            ) {}
        }
        if (!target) {
            TopAppBar(
                title = {
                    Text(
                        text = name,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            isSearch = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}
