package com.example.a009_roomdb

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a009_roomdb.data.Note
import com.example.a009_roomdb.data.NoteHubDatabaseInstance
import com.example.a009_roomdb.databinding.ActivityAddNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val noteDao = NoteHubDatabaseInstance.getDatabase(this).noteDao()

        binding.btnSaveNote.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val content = binding.editTextContent.text.toString()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Vyplň název i obsah", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val note = Note(title = title, content = content)

            // DB operace běží na vlákně pro IO, aby se nezablokovalo UI
            CoroutineScope(Dispatchers.IO).launch {
                noteDao.insert(note)
                finish() // návrat na hlavní obrazovku
            }
        }
    }
}
