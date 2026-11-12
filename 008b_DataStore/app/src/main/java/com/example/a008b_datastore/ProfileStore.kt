package com.example.a008b_datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

// DataStore na úrovni souboru (top-level) - jedna instance pro celou aplikaci
val Context.dataStore by preferencesDataStore(name = "gamer_profile")

// Typově bezpečné klíče
val NICKNAME_KEY = stringPreferencesKey("NICKNAME")
val HARDCORE_KEY = booleanPreferencesKey("HARDCORE")
