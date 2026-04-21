package com.example.seriesexplorer.data.remote.dto.tvmaze

data class EpisodeDto(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val airdate: String?,
    val runtime: Int?
)