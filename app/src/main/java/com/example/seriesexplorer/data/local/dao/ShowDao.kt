package com.example.seriesexplorer.data.local.dao

import androidx.room.*
import com.example.seriesexplorer.data.local.entity.ShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShows(shows: List<ShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShow(show: ShowEntity)

    @Query("SELECT * FROM shows")
    fun getAllShows(): Flow<List<ShowEntity>>

    @Query("SELECT * FROM shows WHERE id = :id")
    suspend fun getShowById(id: Int): ShowEntity?

    @Query("DELETE FROM shows")
    suspend fun deleteAllShows()

    @Delete
    suspend fun deleteShow(show: ShowEntity)
}