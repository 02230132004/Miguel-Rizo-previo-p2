package com.example.seriesexplorer.data.local.dao

import androidx.room.*
import com.example.seriesexplorer.data.local.entity.EpisodeEntity

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodes WHERE showId = :showId")
    suspend fun getEpisodesByShowId(showId: Int): List<EpisodeEntity>

    @Query("DELETE FROM episodes WHERE showId = :showId")
    suspend fun deleteEpisodesByShowId(showId: Int)
}