package com.snowdango.bijouk.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.snowdango.bijouk.features.search.album.SearchAlbumsScreen
import com.snowdango.bijouk.features.search.artist.SearchArtistsScreen
import com.snowdango.bijouk.features.search.songs.SearchSongsScreen
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    baseUrl: String,
    token: String,
    sheetMinSize: Dp,
    onNavigateArtist: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel<SearchViewModel> {
        parametersOf(baseUrl, token)
    },
) {
    val scope = rememberCoroutineScope()
    val state = rememberPagerState(initialPage = 0) { SearchContentRoute.entries.size }

    val searchSongs = viewModel.searchSongsFlow.collectAsLazyPagingItems()
    val searchAlbums = viewModel.searchAlbumsFlow.collectAsLazyPagingItems()
    val searchArtists = viewModel.searchArtistsFlow.collectAsLazyPagingItems()

    val keyboardController = LocalSoftwareKeyboardController.current
    var inputString by remember { mutableStateOf("") }
    val screenWidth = LocalConfiguration.current.screenWidthDp

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
            TabRow(
                selectedTabIndex = state.currentPage,
                modifier = Modifier.width(screenWidth.dp),
            ) {
                SearchContentRoute.entries.forEachIndexed { index, pageRoute ->
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
                val route = SearchContentRoute.entries[it]
                when (route) {
                    SearchContentRoute.SONG -> {
                        SearchSongsScreen(
                            baseUrl = baseUrl,
                            token = token,
                            sheetMinSize = sheetMinSize,
                            modifier = Modifier.fillMaxSize(),
                            searchSongs = searchSongs,
                        )
                    }

                    SearchContentRoute.ALBUM -> {
                        SearchAlbumsScreen(
                            baseUrl = baseUrl,
                            token = token,
                            sheetMinSize = sheetMinSize,
                            modifier = Modifier.fillMaxSize(),
                            searchAlbums = searchAlbums,
                        )
                    }

                    SearchContentRoute.ARTIST -> {
                        SearchArtistsScreen(
                            sheetMinSize = sheetMinSize,
                            modifier = Modifier.fillMaxSize(),
                            searchArtists = searchArtists,
                            onClickArtist = onNavigateArtist,
                        )
                    }
                }
            }
        }
    }
}
