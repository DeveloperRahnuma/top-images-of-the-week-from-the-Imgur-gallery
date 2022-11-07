package com.topimage.imgurgallery.data.repositories

import com.topimage.imgurgallery.data.db.Database
import com.topimage.imgurgallery.data.network.MyApi
import com.topimage.imgurgallery.data.network.SafeApiRequest
import com.topimage.imgurgallery.data.network.responses.ImageResponce

class UserRepository(
    private val api: MyApi,
    private val db: Database
) : SafeApiRequest() {

    suspend fun getWeekTopImage(email: String, password: String): ImageResponce {
        return apiRequest { api.getWeekTopImage() }
    }

}