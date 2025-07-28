package com.snowdango.bijouk.features.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.bijouk.features.library.album.LibraryAlbumsScreen
import com.snowdango.bijouk.features.library.artist.LibraryArtistsScreen
import com.snowdango.bijouk.features.library.playlist.LibraryPlaylistScreen
import com.snowdango.bijouk.features.library.songs.LibrarySongsScreen
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    onNavigateArtist: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LibraryViewModel = koinViewModel<LibraryViewModel> {
        parametersOf(baseUrl, token)
    },
) {
    val scope = rememberCoroutineScope()
    val state = rememberPagerState(initialPage = 0) { LibraryContentRoute.entries.size }

    val searchSongs = viewModel.searchSongsFlow.collectAsLazyPagingItems()
    val searchAlbums = viewModel.searchAlbumsFlow.collectAsLazyPagingItems()
    val searchArtists = viewModel.searchArtistsFlow.collectAsLazyPagingItems()
    val searchPlaylists = viewModel.searchPlaylistsFlow.collectAsLazyPagingItems()

    val keyboardController = LocalSoftwareKeyboardController.current
    var inputString by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = inputString,
                        onQueryChange = { inputString = it },
                        onSearch = {
                            keyboardController?.hide()
                            viewModel.setQuery(inputString)
                            searchSongs.refresh()
                            searchAlbums.refresh()
                            searchArtists.refresh()
                            searchPlaylists.refresh()
                        },
                        expanded = false,
                        onExpandedChange = { },
                        placeholder = { Text(text = stringResource(R.string.top_bar_search_placeholder)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    )
                },
                expanded = false,
                onExpandedChange = { },
                content = {},
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PrimaryTabRow(
                selectedTabIndex = state.currentPage,
                modifier = Modifier.fillMaxWidth(),
            ) {
                LibraryContentRoute.entries.forEachIndexed { index, pageRoute ->
                    Tab(
                        selected = state.currentPage == index,
                        text = {
                            Text(text = stringResource(pageRoute.titleRes))
                        },
                        onClick = {
                            scope.launch {
                                state.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
            HorizontalPager(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                val route = LibraryContentRoute.entries[it]
                when (route) {
                    LibraryContentRoute.SONG -> {
                        LibrarySongsScreen(
                            baseUrl = baseUrl,
                            token = token,
                            sheetMinSize = sheetMinSize,
                            modifier = Modifier.fillMaxSize(),
                            searchSongs = searchSongs,
                        )
                    }

                    LibraryContentRoute.ALBUM -> {
                        LibraryAlbumsScreen(
                            baseUrl = baseUrl,
                            token = token,
                            sheetMinSize = sheetMinSize,
                            modifier = Modifier.fillMaxSize(),
                            searchAlbums = searchAlbums,
                        )
                    }

                    LibraryContentRoute.ARTIST -> {
                        LibraryArtistsScreen(
                            sheetMinSize = sheetMinSize,
                            modifier = Modifier.fillMaxSize(),
                            searchArtists = searchArtists,
                            onClickArtist = onNavigateArtist,
                        )
                    }

                    LibraryContentRoute.PLAYLIST -> {
                        LibraryPlaylistScreen(
                            baseUrl = baseUrl,
                            token = token,
                            sheetMinSize = sheetMinSize,
                            searchPlaylist = searchPlaylists,
                            modifier = Modifier.fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}
