package com.example.mvvm_lesson.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_lesson.database.AppDatabase
import com.example.mvvm_lesson.networking.ApiService
import com.example.mvvm_lesson.utils.NetworkHelper

class UserViewModelFactory(
    private val appDatabase: AppDatabase,
    private val apiService: ApiService,
    private val networkHelper: NetworkHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(appDatabase, apiService, networkHelper) as T
        }
        return throw Exception("UserViewModelFactory Error")
    }
}