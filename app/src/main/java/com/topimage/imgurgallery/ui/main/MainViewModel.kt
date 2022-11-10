package com.topimage.imgurgallery.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.topimage.imgurgallery.data.network.responses.ImageDetails
import com.topimage.imgurgallery.data.repositories.UserRepository
import com.topimage.imgurgallery.utill.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeekTopImage(searchImage : String) : Flow<Resource<ImageDetails>> {
        return repository.getWeekTopImage(searchImage)
    }

}