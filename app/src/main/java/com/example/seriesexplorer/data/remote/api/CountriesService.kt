package com.example.seriesexplorer.data.remote.api

import com.example.seriesexplorer.data.remote.dto.countries.CountryResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountriesService {
    companion object {
        const val BASE_URL = "https://restcountries.com"
    }

    @GET("/v3.1/name/{name}")
    suspend fun getCountryByName(
        @Path("name") name: String,
        @Query("fields") fields: String = "name,cca2,flags"
    ): List<CountryResponseDto>
}