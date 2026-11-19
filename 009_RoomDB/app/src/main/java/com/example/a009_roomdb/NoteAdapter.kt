package com.example.a009_roomdb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a009_roomdb.data.Note
import com.example.a009_roomdb.databinding.ItemNoteBinding

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // Seznam poznámek, které se mají zobrazit
    private var notes: List<Note> = emptyList()

    // ViewHolder drží binding jednoho itemu (jedné poznámky)
    class NoteViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Vytvoří nový ViewHolder – grafiku jedné položky v seznamu
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(binding)
    }

    // Naplní jednu položku daty z konkrétní poznámky
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.binding.tvNoteTitle.text = currentNote.title
        holder.binding.tvNoteContent.text = currentNote.content
    }

    // Počet poznámek → RecyclerView ví, kolik řádků vykreslit
    override fun getItemCount(): Int = notes.size

    // Aktualizuje seznam poznámek a obnoví zobrazení
    fun submitList(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
