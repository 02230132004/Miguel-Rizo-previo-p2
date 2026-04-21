package com.example.seriesexplorer.data.remote.api

import com.example.seriesexplorer.data.remote.dto.tvmaze.EpisodeDto
import com.example.seriesexplorer.data.remote.dto.tvmaze.ShowDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeService {
    companion object {
        const val BASE_URL = "https://api.tvmaze.com"
    }

    @GET("/shows")
    suspend fun getShows(@Query("page") page: Int): List<ShowDto>

    @GET("/shows/{id}")
    suspend fun getShowDetail(@Path("id") id: Int): ShowDto

    @GET("/shows/{id}/episodes")
    suspend fun getEpisodesByShow(@Path("id") id: Int): List<EpisodeDto>
}