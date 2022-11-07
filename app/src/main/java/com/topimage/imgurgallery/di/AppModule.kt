package com.topimage.imgurgallery.di

import android.app.Application
import androidx.room.Room
import com.topimage.imgurgallery.data.db.Database
import com.topimage.imgurgallery.data.db.DatabaseRepository
import com.topimage.imgurgallery.data.db.DatabaseRepositoryImpl
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

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDatabase(app: Application): Database {
        return Room.databaseBuilder(app, Database::class.java, "imgur_db").build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: Database): DatabaseRepository {
        return DatabaseRepositoryImpl(db.dao)
    }

    @Provides
    @Singleton
    fun provideApi(): MyApi {
        return Retrofit.Builder().baseUrl("https://api.imgur.com/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build().create()
    }


}