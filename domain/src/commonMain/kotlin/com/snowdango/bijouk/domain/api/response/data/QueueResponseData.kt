package com.snowdango.bijouk.domain.api.response.data


import com.snowdango.bijouk.domain.api.entity.Artwork
import com.snowdango.bijouk.domain.api.entity.PlayParams
import com.snowdango.bijouk.domain.api.entity.Preview
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("UnsafeOptInUsageError")
@Serializable
data class QueueResponseData(
    @SerialName("_songId")
    val songId: String? = null,
    @SerialName("_state")
    val state: QueueState,
    val assetURL: String? = null,
    val assets: List<QueueAsset>,
    val attributes: QueueAttributes,
    val flavor: String? = null,
    val id: String,
    val playbackType: Int,
    val type: String
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class QueueState(
    val current: Int,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class QueueAsset(
    @SerialName("URL")
    val url: String,
    val artworkURL: String? = null,
    val chunks: QueueChunks? = null,
    val downloadKey: String? = null,
    @SerialName("file-size")
    val fileSize: Int? = null,
    val flavor: String? = null,
    val md5: String,
    val metadata: QueueMetadata,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class QueueChunks(
    val chunkSize: Int,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class QueueAttributes(
    val albumName: String,
    val artistName: String? = null,
    val artwork: Artwork? = null,
    val audioLocale: String? = null,
    val audioTraits: List<String>? = null,
    val composerName: String? = null,
    val currentPlaybackTime: Double? = null,
    val discNumber: Int,
    val durationInMillis: Int,
    val genreNames: List<String>,
    val hasLyrics: Boolean,
    val hasTimeSyncedLyrics: Boolean? = null,
    val isAppleDigitalMaster: Boolean? = null,
    val isMasteredForItunes: Boolean? = null,
    val isVocalAttenuationAllowed: Boolean? = null,
    val isrc: String? = null,
    val name: String,
    val playParams: PlayParams,
    val previews: List<Preview>? = null,
    val releaseDate: String? = null,
    val remainingTime: Double? = null,
    val trackNumber: Int,
    val url: String? = null,
)

@Suppress("UnsafeOptInUsageError")
@Serializable
data class QueueMetadata(
    val artistId: String? = null,
    val artistName: String,
    val bitRate: Int,
    val compilation: Boolean,
    val composerId: String? = null,
    val composerName: String? = null,
    val copyright: String? = null,
    val discCount: Int,
    val discNumber: Int,
    val duration: Int,
    val explicit: Int,
    val fileExtension: String,
    val gapless: Boolean,
    val genre: String,
    val genreId: Int? = null,
    val isMasteredForItunes: Boolean,
    val itemId: String,
    val itemName: String,
    val kind: String,
    val playlistArtistName: String,
    val playlistId: String? = null,
    val playlistName: String,
    val rank: Int? = null,
    val releaseDate: String? = null,
    val s: Int? = null,
    val sampleRate: Int? = null,
    @SerialName("sort-album")
    val sortAlbum: String,
    @SerialName("sort-artist")
    val sortArtist: String,
    @SerialName("sort-composer")
    val sortComposer: String,
    @SerialName("sort-name")
    val sortName: String,
    val trackCount: Int,
    val trackNumber: Int,
    val vendorId: Int? = null,
    val xid: String? = null,
    val year: Int
)
