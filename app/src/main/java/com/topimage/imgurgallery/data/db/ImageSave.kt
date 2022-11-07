package com.topimage.imgurgallery.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

// Entity class basically a table for room and its
// variable will act as column name of table
@Entity
data class ImageSave(
    val title: String,
    val description: String?,
    val isDone: Boolean,
    @PrimaryKey val id: Int? = null
)
