package com.example.mvvm_lesson.database.dao

import android.service.autofill.UserData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvm_lesson.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    fun addUser(userEntity: UserEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(usersList: List<UserEntity>)

    @Query("select * from userentity")
    fun getUsers(): List<UserEntity>

    @Query("select count(*) from userentity")

    fun getUserCount(): Int

}