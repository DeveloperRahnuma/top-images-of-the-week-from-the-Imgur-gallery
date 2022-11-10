package com.topimage.imgurgallery.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchString(
    val searchString: String,
    @PrimaryKey val id: Int? = null
)