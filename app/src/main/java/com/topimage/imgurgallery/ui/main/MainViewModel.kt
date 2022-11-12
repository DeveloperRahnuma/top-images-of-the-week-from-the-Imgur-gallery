package com.topimage.imgurgallery.ui.main

import androidx.lifecycle.ViewModel
import com.topimage.imgurgallery.data.network.responses.AlbumResponce
import com.topimage.imgurgallery.data.repositories.UserRepository
import com.topimage.imgurgallery.utill.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {


    // this function will call the repository function and get the data from there
    // and pass it to the view
    suspend fun getImage(
        searchImageName: String,
        fetchedAlbumData : (imageInfoList: List<AlbumResponce>) -> Unit,
        showLoading : () -> Unit,
        showNoData : ()-> Unit,
        ) {
        withContext(Dispatchers.IO) {
            repository.getWeekTopImage(searchImageName)?.collect {
                when (it) {
                    is Resource.Success -> {
                        it.Data?.let { it1 -> it1?.data?.let { it2 -> fetchedAlbumData(it2) } }
                    }
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Error -> {
                        showNoData()
                    }
                }
            }

        }
    }

}