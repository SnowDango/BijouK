package com.snowdango.bijouk.features.album

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.snowdango.bijouk.model.cider.CiderModel
import com.snowdango.bijouk.model.cider.CiderRPCModel
import com.snowdango.bijouk.model.cider.data.AlbumDetailData
import com.snowdango.bijouk.model.cider.data.entity.SongData
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class AlbumDetailViewModel(
    private val baseUrl: String,
    private val token: String,
    private val albumId: String,
    private val isLibrary: Boolean,
) : ViewModel(), KoinComponent {

    private val ciderModel: CiderModel by inject { parametersOf(baseUrl, token) }
    private val ciderRPCModel: CiderRPCModel by inject { parametersOf(baseUrl, token) }
    private val applicationScope: CoroutineScope by inject()

    private val _albumDetailDataFlow: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val albumDetailDataFlow = _albumDetailDataFlow.stateIn(
        applicationScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = _albumDetailDataFlow.value,
    )
    val albumSongsFlow: Flow<PagingData<SongData>> = Pager(
        config = PagingConfig(
            pageSize = 100,
            initialLoadSize = 100,
        )
    ) {
        ciderModel.getAlbumSongsPagingSource(isLibrary, albumId)
    }.flow.cachedIn(viewModelScope)

    init {
        load()
    }

    private fun load() = viewModelScope.launch(Dispatchers.IO) {
        getAlbumDetail()
    }

    private fun getAlbumDetail() = viewModelScope.launch(Dispatchers.IO) {
        _albumDetailDataFlow.emit(UiState.Loading)
        try {
            val albumDetail = if (isLibrary) {
                ciderModel.getLibraryAlbumDetails(albumId)
            } else {
                ciderModel.getAlbumDetails(albumId)
            }
            if (albumDetail != null) {
                _albumDetailDataFlow.emit(UiState.Success(albumDetail))
            } else {
                _albumDetailDataFlow.emit(UiState.Error)
            }
        } catch (ce: CancellationException) {
            throw ce
        } catch (th: Throwable) {
            Log.e("AlbumDetailViewModel", th.toString())
            _albumDetailDataFlow.emit(UiState.Error)
        }
    }


    sealed class UiState {
        data object Loading : UiState()
        data class Success(val albumDetailData: AlbumDetailData) : UiState()
        data object Error : UiState()
    }
}