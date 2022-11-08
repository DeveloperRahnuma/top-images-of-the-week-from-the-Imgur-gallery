package com.topimage.imgurgallery.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.topimage.imgurgallery.R
import com.topimage.imgurgallery.data.network.responses.AlbumResponce
import com.topimage.imgurgallery.extension.loadImageOrDefault

class MainRecycleAdapter(val context: Context,private val image: List<AlbumResponce>) : RecyclerView.Adapter<MainRecycleAdapter.ViewHolder>(){

    // holder class to hold reference
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //get view reference
        val imageTitleText: TextView = view.findViewById(R.id.titleText)
        val imageNumber: TextView = view.findViewById(R.id.imageNumber)
        val imageDateAndTime: TextView = view.findViewById(R.id.imageDateAndTime)
        val imageThumb: ImageView = view.findViewById(R.id.imageThumb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageTitleText.text = image[position].title
        holder.imageNumber.text = "1 / ${image[position].images_count}"
        holder.imageDateAndTime.text = image[position].datetime
        image[position].images?.get(0)?.link?.let { holder.imageThumb.loadImageOrDefault(it) }
    }

    override fun getItemCount(): Int {
        return image.size
    }

}