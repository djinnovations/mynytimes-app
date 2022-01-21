package com.example.mynytimeapp.remote


interface ExecutionListener<T> {
    fun onSuccess(response: T)
    fun onApiError(error: NetworkError)
    fun onFailure(error: Throwable)
}