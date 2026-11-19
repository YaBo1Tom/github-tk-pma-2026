package com.example.a009_roomdb

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a009_roomdb.data.Note
import com.example.a009_roomdb.data.NoteDao
import com.example.a009_roomdb.data.NoteHubDatabaseInstance
import com.example.a009_roomdb.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteDao: NoteDao
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Po kliknutí na FAB otevře AddNoteActivity
        binding.fabAddNote.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        /* Zobrazování poznámek */

        // 1) Adaptér s callbacky pro editaci a mazání
        adapter = NoteAdapter(
            onEditClick = { note ->
                // editace bude doplněna později
            },
            onDeleteClick = { note ->
                deleteNote(note)
            }
        )

        // 2) Nastavení RecyclerView
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNotes.adapter = adapter

        // 3) DAO
        noteDao = NoteHubDatabaseInstance.getDatabase(applicationContext).noteDao()

        // 4) Pozorování Flow z Room – korutina je vázaná na životní cyklus aktivity
        lifecycleScope.launch {
            noteDao.getAllNotes().collectLatest { notes ->
                adapter.submitList(notes)
            }
        }
    }

    private fun deleteNote(note: Note) {
        // DB operace na IO vlákně; korutina se ukončí se zničením aktivity
        lifecycleScope.launch(Dispatchers.IO) {
            noteDao.delete(note)
        }
    }
}
