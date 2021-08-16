package ar.com.udemy.exampleapp.core.data

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(private val exception: Exception) : Resource<T>(message = exception.message)
}