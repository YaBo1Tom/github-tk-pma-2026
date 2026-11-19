package com.example.a009_roomdb.data

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(
    entities = [Note::class],
    version = 2,
    exportSchema = false
)
abstract class NoteHubDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
