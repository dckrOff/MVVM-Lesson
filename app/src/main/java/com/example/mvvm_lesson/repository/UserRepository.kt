package com.example.mvvm_lesson.repository

import android.service.autofill.UserData
import com.example.mvvm_lesson.database.dao.UserDao
import com.example.mvvm_lesson.database.entity.UserEntity
import com.example.mvvm_lesson.networking.ApiService
import kotlinx.coroutines.flow.flow

class UserRepository(private val apiService: ApiService, private val userDao: UserDao) {

    fun getUsers() = apiService.getUsers()

    fun addUsers(usersList: List<UserEntity>) = flow { emit(userDao.addUsers(usersList)) }

    fun getDatabaseUsers() = userDao.getUsers()

    fun getUserCount() = userDao.getUserCount()
}