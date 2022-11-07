package com.topimage.imgurgallery.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.topimage.imgurgallery.R
import com.topimage.imgurgallery.data.network.responses.ImageResponce

class MainRecycleAdapter(private val image: ArrayList<ImageResponce>) : RecyclerView.Adapter<MainRecycleAdapter.ViewHolder>(){

    // holder class to hold reference
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //get view reference

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return image.size
    }

}