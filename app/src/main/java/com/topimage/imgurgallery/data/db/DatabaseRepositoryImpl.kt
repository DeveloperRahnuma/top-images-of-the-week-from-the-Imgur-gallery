package com.topimage.imgurgallery.data.db

import com.topimage.imgurgallery.data.db.entity.ImageSave
import kotlinx.coroutines.flow.Flow

class DatabaseRepositoryImpl(
    private val dao: DatabaseDao
): DatabaseRepository {

    override suspend fun insertImageRecord(imageSave: ImageSave) {
        dao.insertTodo(imageSave)
    }

    override suspend fun deleteImageRecord(imageSave: ImageSave) {
        dao.deleteTodo(imageSave)
    }

    override suspend fun getTodoById(id: Int): ImageSave? {
        return dao.getTodoById(id)
    }

    override fun getTodos(): Flow<List<ImageSave>> {
        return dao.getTodos()
    }
}