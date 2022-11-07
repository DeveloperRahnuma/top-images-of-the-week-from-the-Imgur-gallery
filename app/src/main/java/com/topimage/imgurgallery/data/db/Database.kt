package com.topimage.imgurgallery.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.topimage.imgurgallery.data.db.DatabaseDao
import com.topimage.imgurgallery.data.db.ImageSave

// =======================================================================
// Room Database
// =========================================================================
@Database(
    entities = [ImageSave::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract val dao: DatabaseDao
}