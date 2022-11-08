package com.topimage.imgurgallery.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.topimage.imgurgallery.data.network.responses.AlbumResponce
import com.topimage.imgurgallery.databinding.ActivityMainBinding
import com.topimage.imgurgallery.utill.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // for use debug things under this activity
    val TAG = "MainActivity_Debug"
    // for bind the view with this activity
    lateinit var binding: ActivityMainBinding
    private var viewModel : MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ActivityMainBinding generated from xml file name
        //that's bind with this activity to display content here
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        getImage()

    }

    private fun getImage(){
        GlobalScope.launch(Dispatchers.IO) {
            viewModel?.getWeekTopImage()?.collect{
                when(it){
                    is Resource.Success -> {
                        it.Data?.let { it1 -> setUpRecycleView(it1.data) }
                    }
                    is Resource.Loading -> {
                        Log.i(TAG, "Loading State")
                    }
                    else -> {
                        Log.i(TAG, "Loading Errror state")
                    }
                }
            }
        }
    }


    // set up the RecyclerView
    private fun setUpRecycleView(imageInfoList: List<AlbumResponce>){
        runOnUiThread(){
            binding.recycleView.layoutManager = LinearLayoutManager(this)
            val adapter = MainRecycleAdapter(this@MainActivity,imageInfoList)
            binding.recycleView.adapter = adapter
        }
    }

}