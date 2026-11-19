package com.example.a009_roomdb.data

import android.content.Context
import androidx.room3.Room
import androidx.room3.migration.Migration
import androidx.sqlite.execSQL

object NoteHubDatabaseInstance {

    // @Volatile - změna INSTANCE je okamžitě viditelná všem vláknům
    @Volatile
    private var INSTANCE: NoteHubDatabase? = null

    /**
     * MIGRACE z verze 1 na 2: přidáme sloupce createdAt (Long) a category (String).
     * Sloupce nesmí být NULL, proto mají DEFAULT hodnotu pro již existující řádky.
     */
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override suspend fun migrate(connection: androidx.sqlite.SQLiteConnection) {
            connection.execSQL("ALTER TABLE note_table ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0")
            connection.execSQL("ALTER TABLE note_table ADD COLUMN category TEXT NOT NULL DEFAULT 'General'")
        }
    }

    /** Vrátí instanci databáze (singleton). Pokud ještě neexistuje, vytvoří ji. */
    fun getDatabase(context: Context): NoteHubDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                NoteHubDatabase::class.java,
                "notehub_database"
            )
                .addMigrations(MIGRATION_1_2)
                .build()
            INSTANCE = instance
            instance
        }
    }
}
