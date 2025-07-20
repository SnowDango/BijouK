package com.snowdango.bijouk.features.nowPlay.action

sealed class SearchArtistsAction {
    data object Blank : SearchArtistsAction()
    data class SearchArtists(val query: String) : SearchArtistsAction()
}
