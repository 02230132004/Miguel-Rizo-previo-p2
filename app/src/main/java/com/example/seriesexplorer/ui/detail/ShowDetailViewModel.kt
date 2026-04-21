package com.example.seriesexplorer.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesexplorer.domain.repository.ShowRepository
import com.example.seriesexplorer.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    private val repository: ShowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ShowDetailUiState>(ShowDetailUiState.Loading)
    val uiState: StateFlow<ShowDetailUiState> = _uiState

    fun fetchShowDetail(id: Int) {
        viewModelScope.launch {
            repository.getShowDetail(id).collect { result ->
                _uiState.value = when (result) {
                    is ResultWrapper.Loading -> ShowDetailUiState.Loading
                    is ResultWrapper.Success -> {
                        ShowDetailUiState.Success(result.data.first, result.data.second)
                    }
                    is ResultWrapper.Error -> ShowDetailUiState.Error(result.error.toString())
                }
            }
        }
    }
}