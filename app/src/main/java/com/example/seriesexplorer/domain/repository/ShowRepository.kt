package com.example.seriesexplorer.domain.repository

import com.example.seriesexplorer.domain.model.Episode
import com.example.seriesexplorer.domain.model.Show
import com.example.seriesexplorer.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface ShowRepository {
    fun getShows(page: Int): Flow<ResultWrapper<List<Show>>>
    fun getShowDetail(id: Int): Flow<ResultWrapper<Pair<Show, List<Episode>>>>
}