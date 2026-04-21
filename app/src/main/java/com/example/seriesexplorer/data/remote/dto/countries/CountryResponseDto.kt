package com.example.seriesexplorer.data.remote.dto.countries

data class CountryResponseDto(
    val name: CountryNameDto?,
    val cca2: String?,
    val flags: CountryFlagsDto?
)

data class CountryNameDto(
    val common: String?,
    val official: String?
)

data class CountryFlagsDto(
    val png: String?,
    val svg: String?,
    val alt: String?
)