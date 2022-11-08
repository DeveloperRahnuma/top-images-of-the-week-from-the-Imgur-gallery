package com.topimage.imgurgallery.data.network.responses

import com.squareup.moshi.Json

class ImageDetails(){
    lateinit var data : List<AlbumResponce>
}

class AlbumResponce(
    @field:Json(name = "id") val albumID : String?,
    @field:Json(name = "images_count") val images_count: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "datetime") val datetime: String?,
    @field:Json(name = "images") val images : ArrayList<ImageDetailInAlbum>?,
)

data class ImageDetailInAlbum(
    @field:Json(name = "id") val imageID : String? ,
    @field:Json(name = "link") val link : String?,
)