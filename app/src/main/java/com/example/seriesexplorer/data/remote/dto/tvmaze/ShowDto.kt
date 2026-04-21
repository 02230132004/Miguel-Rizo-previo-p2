package com.example.seriesexplorer.data.remote.dto.tvmaze

import com.google.gson.annotations.SerializedName

data class ShowDto(
    val id: Int,
    val name: String,
    val language: String?,
    val genres: List<String>?,
    val status: String?,
    val image: ImageDto?,
    val summary: String?,
    val premiered: String?,
    val network: NetworkDto?
)

data class ImageDto(
    val medium: String?,
    val original: String?
)

data class NetworkDto(
    val name: String?,
    val country: CountryDto?
)

data class CountryDto(
    val name: String?,
    val code: String?
)