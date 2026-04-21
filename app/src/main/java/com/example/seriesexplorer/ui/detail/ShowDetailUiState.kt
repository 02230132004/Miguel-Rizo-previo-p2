package com.example.seriesexplorer.ui.detail

import com.example.seriesexplorer.domain.model.Episode
import com.example.seriesexplorer.domain.model.Show

sealed class ShowDetailUiState {
    object Loading : ShowDetailUiState()
    data class Success(val show: Show, val episodes: List<Episode>) : ShowDetailUiState()
    data class Error(val message: String) : ShowDetailUiState()
    object Empty : ShowDetailUiState()
}