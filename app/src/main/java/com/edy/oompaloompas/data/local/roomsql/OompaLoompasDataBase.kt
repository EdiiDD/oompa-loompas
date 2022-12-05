package com.edy.oompaloompas.data.local.roomsql

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edy.oompaloompas.data.models.OompaLoompaLocalDTO


@Database(
    entities = [OompaLoompaLocalDTO::class],
    version = 1
)
abstract class OompaLoompasDataBase : RoomDatabase() {
    abstract fun oompaLoompasDao(): IOompaLoompasDao
}