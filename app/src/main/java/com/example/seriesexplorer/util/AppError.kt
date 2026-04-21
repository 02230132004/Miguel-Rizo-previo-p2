package com.example.seriesexplorer.util

sealed class AppError {
    object NoInternet : AppError()
    object Timeout : AppError()
    object NotFound : AppError()
    data class ServerError(val code: Int) : AppError()
    data class Unknown(val message: String) : AppError()

    fun toMessage(): String {
        return when (this) {
            is NoInternet -> "Sin conexión a internet. Por favor, verifica tu red."
            is Timeout -> "El servidor tardó mucho en responder (Timeout)."
            is NotFound -> "No se encontró la información solicitada."
            is ServerError -> "Error en el servidor. Código: $code"
            is Unknown -> "Ocurrió un error inesperado: $message"
        }
    }
}