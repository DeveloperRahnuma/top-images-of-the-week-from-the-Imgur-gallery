package com.topimage.imgurgallery.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.topimage.imgurgallery.data.db.entity.ImageSave
import com.topimage.imgurgallery.data.db.entity.SearchString

// =======================================================================
// Room Database
// =========================================================================
@Database(
    entities = [ImageSave::class, SearchString::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val dao: DatabaseDao
}