package com.example.seriesexplorer.util

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val error: AppError) : ResultWrapper<Nothing>()
    object Loading : ResultWrapper<Nothing>()
}