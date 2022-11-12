package com.topimage.imgurgallery.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.topimage.imgurgallery.R
import com.topimage.imgurgallery.data.network.responses.AlbumResponce
import com.topimage.imgurgallery.extension.loadImageOrDefault

class MainRecycleAdapter(private val image: List<AlbumResponce>) : RecyclerView.Adapter<MainRecycleAdapter.ViewHolder>(){

    // holder class to hold reference
    // get view reference of item design which is in list_item
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // here we will display the description of image
        val imageTitleText: TextView = view.findViewById(R.id.titleText)

        // we will use this view to display number of image total have for that image group
        val imageNumber: TextView = view.findViewById(R.id.imageNumber)

        // we will use this view to display time and date
        val imageDateAndTime: TextView = view.findViewById(R.id.imageDateAndTime)

        // we use this image view to display the image from url by help of glide
        val imageThumb: ImageView = view.findViewById(R.id.imageThumb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
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