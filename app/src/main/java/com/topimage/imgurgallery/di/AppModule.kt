package com.topimage.imgurgallery.di

import com.topimage.imgurgallery.data.network.MyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

//Dependency injection app module

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // for pass the MyApi instance by the help of hilt
    @Provides
    @Singleton
    fun provideApi(): MyApi {
        return Retrofit.Builder().baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build().create()
    }

}