package com.webservice

import com.MockFileReader
import com.topimage.imgurgallery.data.network.MyApi
import com.topimage.imgurgallery.utill.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MovieApiTest {

    private lateinit var mockWebServer: MockWebServer

    // Executes each task synchronously using Architecture Components.

    private lateinit var webClient: MyApi

    @Before
    fun initService(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        webClient = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)
    }

    @Throws(IOException::class)
    fun mockResponseFromJson(fileName: String) {
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse.setBody(
                MockFileReader().getResponseFromJson(fileName)
            )
        )
    }

    @Test
    fun testMovieListFromServer() {
        runBlocking {
            mockResponseFromJson("/imageListImgur.json")
            val imageResponce = webClient.getWeekTopImage()
            val data = Resource.Success(imageResponce)
            val listOfImageAlbum = data.Data?.data
            Assert.assertEquals(listOfImageAlbum?.get(0)?.albumID, "tt1745960")
            Assert.assertEquals(data.Data?.data?.size, 60)
        }
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }
}