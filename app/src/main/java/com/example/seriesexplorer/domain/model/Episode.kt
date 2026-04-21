package com.example.seriesexplorer.domain.model

data class Episode(
    val id: Int,
    val showId: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val airdate: String?,
    val runtime: Int?
)