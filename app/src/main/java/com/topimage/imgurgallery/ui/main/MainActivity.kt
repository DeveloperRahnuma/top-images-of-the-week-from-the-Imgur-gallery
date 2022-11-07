package com.topimage.imgurgallery.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.topimage.imgurgallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // for bind the view with this activity
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ActivityMainBinding generated from xml file name
        //that's bind with this activity to display content here
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}