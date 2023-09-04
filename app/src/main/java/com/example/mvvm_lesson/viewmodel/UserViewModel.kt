package com.example.mvvm_lesson.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_lesson.database.AppDatabase
import com.example.mvvm_lesson.database.entity.UserEntity
import com.example.mvvm_lesson.mapper.maptoEntity
import com.example.mvvm_lesson.networking.ApiClient
import com.example.mvvm_lesson.networking.ApiService
import com.example.mvvm_lesson.repository.UserRepository
import com.example.mvvm_lesson.utils.NetworkHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch

class UserViewModel(
    appDatabase: AppDatabase,
    apiService: ApiService,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val userRepository = UserRepository(apiService, appDatabase.userDao())
    private val stateFlow = MutableStateFlow<Resourse<List<UserEntity>>>(Resourse.Loading())

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()) {
                userRepository.getUsers()
                    .catch {
                        stateFlow.emit(Resourse.Error(it))
                    }.flatMapConcat { it ->
                        val list = ArrayList<UserEntity>()
                        it.forEach {
                            list.add(it.maptoEntity(it))
                        }
                        userRepository.addUsers(list)
                    }.collect {
                        stateFlow.emit(Resourse.Success(userRepository.getDatabaseUsers()))
                    }
            } else {
                if (userRepository.getUserCount() > 0) {
                    stateFlow.emit(Resourse.Success(userRepository.getDatabaseUsers()))
                } else {
                    stateFlow.emit(Resourse.Error(Throwable("Internet not connected")))
                }
            }
        }
    }

    fun getStateFlow(): StateFlow<Resourse<List<UserEntity>>> {
        return stateFlow
    }
}