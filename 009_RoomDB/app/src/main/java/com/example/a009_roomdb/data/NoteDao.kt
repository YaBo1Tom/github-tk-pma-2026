package com.example.a009_roomdb.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    // CREATE - vloží novou poznámku
    @Insert
    suspend fun insert(note: Note)

    // READ - všechny poznámky jako Flow (pozorování změn)
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getAllNotes(): Flow<List<Note>>

    // UPDATE - aktualizuje existující poznámku
    @Update
    suspend fun update(note: Note)

    // DELETE - smaže poznámku
    @Delete
    suspend fun delete(note: Note)
}
