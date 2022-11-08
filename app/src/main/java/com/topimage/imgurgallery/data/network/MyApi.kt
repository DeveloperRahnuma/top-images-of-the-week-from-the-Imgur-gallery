package com.topimage.imgurgallery.data.network

import com.topimage.imgurgallery.data.network.responses.ImageDetails
import retrofit2.http.*

interface MyApi {
    // api for get weekly top image from imgur
    @Headers("Authorization: Client-ID adcd17773fcdcde")
    @GET("gallery/{section}/{sort}/{window}/{page}?showViral=true&mature=true&album_previews=true")
     suspend fun getWeekTopImage(
        @Path("section") email: String = "top",
        @Path("sort") sort: String = "top",
        @Path("page") page: String = "1",
        @Path("window") window: String = "week",
    ) : ImageDetails

}

