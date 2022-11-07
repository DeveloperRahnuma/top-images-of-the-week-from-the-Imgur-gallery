package com.topimage.imgurgallery.data.db

import com.topimage.imgurgallery.data.db.ImageSave
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun insertImageRecord(imageSave: ImageSave)

    suspend fun deleteImageRecord(imageSave: ImageSave)

    suspend fun getTodoById(id: Int): ImageSave?

    fun getTodos(): Flow<List<ImageSave>>
}