package com.topimage.imgurgallery.ui.main

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferfalk.simplesearchview.SimpleSearchView
import com.topimage.imgurgallery.R
import com.topimage.imgurgallery.data.network.responses.AlbumResponce
import com.topimage.imgurgallery.data.network.responses.ImageDetails
import com.topimage.imgurgallery.data.repositories.UserRepository
import com.topimage.imgurgallery.databinding.ActivityMainBinding
import com.topimage.imgurgallery.utill.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {


    // this function will call the repository function and get the data from there
    // and pass it to the view
    fun getImage(
        searchImageName: String,
        fetchedAlbumData : (imageInfoList: List<AlbumResponce>) -> Unit,
        showLoading : () -> Unit,
        showNoData : ()-> Unit,
        ) {
        GlobalScope.launch(Dispatchers.IO) {
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