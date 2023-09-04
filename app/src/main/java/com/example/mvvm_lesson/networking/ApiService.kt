package com.example.mvvm_lesson.networking

import com.example.mvvm_lesson.models.UserData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    fun getUsers(): Flow<List<UserData>>
}