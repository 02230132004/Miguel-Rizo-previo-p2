package com.example.seriesexplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.seriesexplorer.data.local.dao.EpisodeDao
import com.example.seriesexplorer.data.local.dao.ShowDao
import com.example.seriesexplorer.data.local.entity.EpisodeEntity
import com.example.seriesexplorer.data.local.entity.ShowEntity

@Database(entities = [ShowEntity::class, EpisodeEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDao
    abstract fun episodeDao(): EpisodeDao

    companion object {
        const val DATABASE_NAME = "series_explorer_db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE shows ADD COLUMN countryFlag TEXT")
            }
        }
    }
}