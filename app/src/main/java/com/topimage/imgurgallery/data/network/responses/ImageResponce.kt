package com.topimage.imgurgallery.data.network.responses

import com.squareup.moshi.Json


data class ImageResponce(
    @field:Json(name = "link") val imageUrl : String?,
    @field:Json(name = "title") val title : String?,
    @field:Json(name = "images_count") val images_count: String?,
)