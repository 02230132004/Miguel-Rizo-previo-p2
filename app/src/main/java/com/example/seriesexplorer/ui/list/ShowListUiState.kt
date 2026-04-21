package com.example.seriesexplorer.ui.list

import com.example.seriesexplorer.domain.model.Show

sealed class ShowListUiState {
    object Loading : ShowListUiState()
    data class Success(val data: List<Show>) : ShowListUiState()
    data class Error(val message: String) : ShowListUiState()
    object Empty : ShowListUiState()
}