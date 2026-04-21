package com.example.seriesexplorer.domain.model

data class Show(
    val id: Int,
    val name: String,
    val language: String,
    val genres: List<String>,
    val status: String,
    val imageUrl: String?,
    val summary: String?,
    val premiered: String?,
    val countryName: String?,
    val countryCode: String?,
    val countryFlag: String?
)