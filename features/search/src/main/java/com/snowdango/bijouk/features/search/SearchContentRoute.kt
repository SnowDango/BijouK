package com.snowdango.bijouk.features.search

import androidx.annotation.StringRes

enum class SearchContentRoute(@param:StringRes val titleRes: Int) {
    SONG(R.string.content_page_tab_song),
    ALBUM(R.string.content_page_tab_album),
    ARTIST(R.string.content_page_tab_artist),
    PLAYLIST(R.string.content_page_tab_playlist),
}
