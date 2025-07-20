package com.snowdango.bijouk.features.nowPlay.action

sealed class SearchSongsAction {
    data object Blank : SearchSongsAction()
    data class SearchSongs(val query: String) : SearchSongsAction()
}
