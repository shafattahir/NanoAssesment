package shafat.projects.assesment.datasource.constants

sealed class DataState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : DataState<T>(data)
    class Loading<T>(data: T? = null) : DataState<T>(data)
    class Error<T>(message: String, data: T? = null) : DataState<T>(data, message)
}