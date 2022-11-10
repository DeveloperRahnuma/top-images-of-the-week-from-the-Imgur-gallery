package com.topimage.imgurgallery.data.network.responses

import com.squareup.moshi.Json

// This class is depend on the json that api return
// where AlbumResponse wll hold the data of album
// and ImageDetailInAlbum will hold the data of each image inside
// the album
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