package com.topimage.imgurgallery.data.db

import androidx.room.*
import com.topimage.imgurgallery.data.db.ImageSave
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {

    // for insert image details into database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(imageSave: ImageSave)

    // for delete image details into database
    @Delete
    suspend fun deleteTodo(imageSave: ImageSave)

    // get save image details from database where id match with your need
    @Query("SELECT * FROM ImageSave WHERE id = :id")
    suspend fun getTodoById(id: Int): ImageSave?

    // get all save image details from database
    @Query("SELECT * FROM ImageSave")
    fun getTodos(): Flow<List<ImageSave>>
}