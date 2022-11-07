package com.topimage.imgurgallery.ui.main

import androidx.lifecycle.ViewModel
import com.topimage.imgurgallery.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    suspend fun getWeekTopImage() {
        GlobalScope.launch(Dispatchers.IO){
            repository.getWeekTopImage()
        }
    }

}