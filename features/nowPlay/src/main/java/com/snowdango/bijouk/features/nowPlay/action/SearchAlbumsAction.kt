package com.snowdango.bijouk.features.nowPlay.action

sealed class SearchAlbumsAction {
    data object Blank : SearchAlbumsAction()
    data class SearchAlbums(val query: String) : SearchAlbumsAction()
}
