package com.example.mvvm_lesson.mapper

import com.example.mvvm_lesson.database.entity.UserEntity
import com.example.mvvm_lesson.models.UserData

fun UserData.maptoEntity(userData: UserData): UserEntity {
    return UserEntity(id = userData.id, login = userData.login, avatar = userData.avatarUrl)
}