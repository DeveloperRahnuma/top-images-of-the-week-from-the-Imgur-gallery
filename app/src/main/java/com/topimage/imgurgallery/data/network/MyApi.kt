package com.topimage.imgurgallery.data.network

import com.topimage.imgurgallery.data.network.responses.ImageResponce
import retrofit2.Response
import retrofit2.http.*

interface MyApi {
    // api for get weekly top image from imgur
    @FormUrlEncoded
    @GET("3/gallery/{@section}/{sort}/{window}/{page}?showViral=true")
    suspend fun getWeekTopImage(
        @Path("section") email: String = "top",
        @Path("sort") sort: String = "top",
        @Path("page") page: String = "1",
        @Path("window") window: String = "week",
    ) : Response<ImageResponce>

}

