package com.example.mvvm_lesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_lesson.database.AppDatabase
import com.example.mvvm_lesson.databinding.ActivityMainBinding
import com.example.mvvm_lesson.networking.ApiClient
import com.example.mvvm_lesson.networking.ApiService
import com.example.mvvm_lesson.utils.NetworkHelper
import com.example.mvvm_lesson.viewmodel.Resourse
import com.example.mvvm_lesson.viewmodel.UserViewModel
import com.example.mvvm_lesson.viewmodel.UserViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, UserViewModelFactory(
                AppDatabase.getInstance(this), ApiClient.apiService,
                NetworkHelper(this)
            )
        )[UserViewModel::class.java]

//        viewModel.fetchUsers(this)
        launch {
            viewModel.getStateFlow()
                .collect {
                    when (it) {
                        is Resourse.Error -> {
                            Toast.makeText(this@MainActivity,"Error: ${it.e.message}",Toast.LENGTH_LONG).show()
                        }

                        is Resourse.Loading -> {

                        }

                        is Resourse.Success -> {
                            Toast.makeText(this@MainActivity,"Success: ${it.data}",Toast.LENGTH_LONG).show()
                            Log.e(TAG, "onCreate, Success: ${it.data}")
                        }
                    }
                }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

}