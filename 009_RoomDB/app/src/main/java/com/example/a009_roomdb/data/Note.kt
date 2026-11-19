package com.example.a009_roomdb.data

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID poznámky, automaticky generované
    val title: String, // Název poznámky
    val content: String, // Obsah poznámky

    // nový atribut 1 — časové razítko vytvoření
    val createdAt: Long = System.currentTimeMillis(),

    // nový atribut 2 — kategorie
    val category: String = "General"
)
