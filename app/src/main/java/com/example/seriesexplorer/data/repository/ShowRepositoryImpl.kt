package com.example.seriesexplorer.data.repository

import com.example.seriesexplorer.data.local.dao.EpisodeDao
import com.example.seriesexplorer.data.local.dao.ShowDao
import com.example.seriesexplorer.data.mapper.toDomain
import com.example.seriesexplorer.data.mapper.toEntity
import com.example.seriesexplorer.data.remote.api.CountriesService
import com.example.seriesexplorer.data.remote.api.TvMazeService
import com.example.seriesexplorer.domain.model.Episode
import com.example.seriesexplorer.domain.model.Show
import com.example.seriesexplorer.domain.repository.ShowRepository
import com.example.seriesexplorer.util.AppError
import com.example.seriesexplorer.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val tvMazeService: TvMazeService,
    private val countriesService: CountriesService,
    private val showDao: ShowDao,
    private val episodeDao: EpisodeDao
) : ShowRepository {

    private val cacheTtl = TimeUnit.MINUTES.toMillis(15)

    override fun getShows(page: Int): Flow<ResultWrapper<List<Show>>> = flow {
        emit(ResultWrapper.Loading)
        
        try {
            val remoteShows = tvMazeService.getShows(page)
            val entities = remoteShows.map { it.toEntity() }
            showDao.insertShows(entities)
            emit(ResultWrapper.Success(entities.map { it.toDomain() }))
        } catch (e: Exception) {
            // Requerimiento: Si no hay internet, no mostrar nada y dar error
            emit(ResultWrapper.Error(handleError(e)))
        }
    }

    override fun getShowDetail(id: Int): Flow<ResultWrapper<Pair<Show, List<Episode>>>> = flow {
        emit(ResultWrapper.Loading)

        try {
            val showDto = tvMazeService.getShowDetail(id)
            val episodesDto = tvMazeService.getEpisodesByShow(id)
            
            var countryFlag: String? = null
            
            if (showDto.network?.country?.name != null) {
                try {
                    val countryInfo = countriesService.getCountryByName(showDto.network.country.name)
                    countryFlag = countryInfo.firstOrNull()?.flags?.png
                } catch (e: Exception) {}
            }

            val showEntity = showDto.toEntity(countryFlag)
            val episodeEntities = episodesDto.map { it.toEntity(id) }

            showDao.insertShow(showEntity)
            episodeDao.deleteEpisodesByShowId(id)
            episodeDao.insertEpisodes(episodeEntities)

            emit(ResultWrapper.Success(Pair(showEntity.toDomain(), episodeEntities.map { it.toDomain() })))
        } catch (e: Exception) {
            // Requerimiento: Si no hay internet, no mostrar nada y dar error
            emit(ResultWrapper.Error(handleError(e)))
        }
    }

    private fun handleError(e: Exception): AppError {
        return when (e) {
            is IOException -> AppError.NoInternet
            is HttpException -> {
                when (e.code()) {
                    404 -> AppError.NotFound
                    else -> AppError.ServerError(e.code())
                }
            }
            else -> AppError.Unknown(e.message ?: "Error desconocido")
        }
    }
}