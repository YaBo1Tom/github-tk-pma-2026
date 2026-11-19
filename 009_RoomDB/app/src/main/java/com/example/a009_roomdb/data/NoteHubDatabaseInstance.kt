package com.example.a009_roomdb.data

import android.content.Context
import androidx.room3.Room

object NoteHubDatabaseInstance {

    // @Volatile - změna INSTANCE je okamžitě viditelná všem vláknům
    @Volatile
    private var INSTANCE: NoteHubDatabase? = null

    /** Vrátí instanci databáze (singleton). Pokud ještě neexistuje, vytvoří ji. */
    fun getDatabase(context: Context): NoteHubDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteHubDatabase::class.java,
                "notehub_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
