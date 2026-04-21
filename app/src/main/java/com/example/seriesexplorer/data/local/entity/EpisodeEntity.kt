package com.example.seriesexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey val id: Int,
    val showId: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val airdate: String?,
    val runtime: Int?,
    val lastUpdated: Long
)