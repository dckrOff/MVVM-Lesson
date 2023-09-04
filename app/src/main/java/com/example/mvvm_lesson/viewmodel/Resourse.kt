package com.example.mvvm_lesson.viewmodel

sealed class Resourse<T> {
    class Loading<T> : Resourse<T>()
    class Success<T : Any>(val data: T) : Resourse<T>()
    class Error<T : Any>(val e: Throwable) : Resourse<T>()
}