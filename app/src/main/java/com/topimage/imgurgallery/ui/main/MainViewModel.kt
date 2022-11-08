package com.topimage.imgurgallery.ui.main

import androidx.lifecycle.ViewModel
import com.topimage.imgurgallery.data.network.responses.ImageDetails
import com.topimage.imgurgallery.data.repositories.UserRepository
import com.topimage.imgurgallery.utill.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    suspend fun getWeekTopImage() : Flow<Resource<ImageDetails>> {
        return repository.getWeekTopImage()
    }

}