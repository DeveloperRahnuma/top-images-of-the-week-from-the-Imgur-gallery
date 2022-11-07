package com.topimage.imgurgallery.data.network

import com.google.gson.JsonObject
import com.topimage.imgurgallery.data.db.ImageSave
import com.topimage.imgurgallery.data.network.responses.ImageResponce
import com.topimage.imgurgallery.utill.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface MyApi {
    // api for get weekly top image from imgur
    @Headers("Authorization: Client-ID adcd17773fcdcde")
    @GET("gallery/{section}/{sort}/{window}/{page}?showViral=true")
     suspend fun getWeekTopImage(
        @Path("section") email: String = "top",
        @Path("sort") sort: String = "top",
        @Path("page") page: String = "1",
        @Path("window") window: String = "week",
    ) : List<ImageResponce>

}

