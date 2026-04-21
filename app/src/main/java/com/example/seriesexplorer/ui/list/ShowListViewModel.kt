package com.example.seriesexplorer.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seriesexplorer.domain.model.Show
import com.example.seriesexplorer.domain.repository.ShowRepository
import com.example.seriesexplorer.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowListViewModel @Inject constructor(
    private val repository: ShowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ShowListUiState>(ShowListUiState.Loading)
    val uiState: StateFlow<ShowListUiState> = _uiState

    private var currentPage = 0
    private val allShows = mutableListOf<Show>()

    init {
        fetchShows()
    }

    fun fetchShows(isNextPage: Boolean = false) {
        if (isNextPage) currentPage++
        
        viewModelScope.launch {
            repository.getShows(currentPage).collect { result ->
                _uiState.value = when (result) {
                    is ResultWrapper.Loading -> {
                        if (allShows.isEmpty()) ShowListUiState.Loading else _uiState.value
                    }
                    is ResultWrapper.Success -> {
                        if (!isNextPage) {
                            allShows.clear()
                        }
                        allShows.addAll(result.data)
                        
                        if (allShows.isEmpty()) ShowListUiState.Empty 
                        else ShowListUiState.Success(allShows.toList())
                    }
                    is ResultWrapper.Error -> ShowListUiState.Error(result.error.toMessage())
                }
            }
        }
    }

    fun refresh() {
        currentPage = 0
        fetchShows()
    }
}