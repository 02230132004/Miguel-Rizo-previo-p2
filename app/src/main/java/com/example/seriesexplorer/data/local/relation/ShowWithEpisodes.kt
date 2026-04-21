package com.example.seriesexplorer.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.seriesexplorer.data.local.entity.EpisodeEntity
import com.example.seriesexplorer.data.local.entity.ShowEntity

data class ShowWithEpisodes(
    @Embedded val show: ShowEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "showId"
    )
    val episodes: List<EpisodeEntity>
)