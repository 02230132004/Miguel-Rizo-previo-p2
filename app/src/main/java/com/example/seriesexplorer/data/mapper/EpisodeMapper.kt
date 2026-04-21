package com.example.seriesexplorer.data.mapper

import com.example.seriesexplorer.data.local.entity.EpisodeEntity
import com.example.seriesexplorer.data.remote.dto.tvmaze.EpisodeDto
import com.example.seriesexplorer.domain.model.Episode

fun EpisodeDto.toDomain(showId: Int): Episode {
    return Episode(
        id = id,
        showId = showId,
        name = name,
        season = season,
        number = number,
        airdate = airdate,
        runtime = runtime
    )
}

fun EpisodeDto.toEntity(showId: Int): EpisodeEntity {
    return EpisodeEntity(
        id = id,
        showId = showId,
        name = name,
        season = season,
        number = number,
        airdate = airdate,
        runtime = runtime,
        lastUpdated = System.currentTimeMillis()
    )
}

fun EpisodeEntity.toDomain(): Episode {
    return Episode(
        id = id,
        showId = showId,
        name = name,
        season = season,
        number = number,
        airdate = airdate,
        runtime = runtime
    )
}