package com.topimage.imgurgallery.ui.main

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.topimage.imgurgallery.data.network.responses.ImageResponce
import com.topimage.imgurgallery.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


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

    }

    // set up the RecyclerView
    fun setUpRecycleView(){
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        val adapter = MainRecycleAdapter(ArrayList())
        binding.recycleView.adapter = adapter
    }

}