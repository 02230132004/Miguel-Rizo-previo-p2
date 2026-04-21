package com.example.seriesexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class ShowEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val language: String,
    val genres: String, // Stored as comma separated string or using TypeConverter
    val status: String,
    val imageUrl: String?,
    val summary: String?,
    val premiered: String?,
    val countryName: String?,
    val countryCode: String?,
    val countryFlag: String?,
    val lastUpdated: Long
)