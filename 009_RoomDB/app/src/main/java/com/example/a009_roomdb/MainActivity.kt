package com.example.a009_roomdb

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a009_roomdb.data.NoteHubDatabaseInstance
import com.example.a009_roomdb.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        // 1) Adaptér
        val adapter = NoteAdapter()

        // 2) Nastavení RecyclerView
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNotes.adapter = adapter

        // 3) DAO
        val noteDao = NoteHubDatabaseInstance.getDatabase(applicationContext).noteDao()

        // 4) Pozorování Flow z Room – korutina je vázaná na životní cyklus aktivity
        lifecycleScope.launch {
            noteDao.getAllNotes().collectLatest { notes ->
                adapter.submitList(notes)
            }
        }
    }
}
