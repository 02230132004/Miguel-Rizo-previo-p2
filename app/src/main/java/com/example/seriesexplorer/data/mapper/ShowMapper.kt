package com.example.seriesexplorer.data.mapper

import com.example.seriesexplorer.data.local.entity.ShowEntity
import com.example.seriesexplorer.data.remote.dto.tvmaze.ShowDto
import com.example.seriesexplorer.domain.model.Show

fun ShowDto.toDomain(countryFlag: String? = null): Show {
    return Show(
        id = id,
        name = name,
        language = language ?: "Unknown",
        genres = genres ?: emptyList(),
        status = status ?: "Unknown",
        imageUrl = image?.medium,
        summary = summary,
        premiered = premiered,
        countryName = network?.country?.name,
        countryCode = network?.country?.code,
        countryFlag = countryFlag
    )
}

fun ShowDto.toEntity(countryFlag: String? = null): ShowEntity {
    return ShowEntity(
        id = id,
        name = name,
        language = language ?: "Unknown",
        genres = genres?.joinToString(",") ?: "",
        status = status ?: "Unknown",
        imageUrl = image?.medium,
        summary = summary,
        premiered = premiered,
        countryName = network?.country?.name,
        countryCode = network?.country?.code,
        countryFlag = countryFlag,
        lastUpdated = System.currentTimeMillis()
    )
}

fun ShowEntity.toDomain(): Show {
    return Show(
        id = id,
        name = name,
        language = language,
        genres = genres.split(",").filter { it.isNotBlank() },
        status = status,
        imageUrl = imageUrl,
        summary = summary,
        premiered = premiered,
        countryName = countryName,
        countryCode = countryCode,
        countryFlag = countryFlag
    )
}