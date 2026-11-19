package com.example.a009_roomdb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a009_roomdb.data.Note
import com.example.a009_roomdb.databinding.ItemNoteBinding

class NoteAdapter(
    private val onEditClick: (Note) -> Unit,
    private val onDeleteClick: (Note) -> Unit
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

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

        // Kliknutí na ikonu EDIT / DELETE předáme ven (řeší MainActivity)
        holder.binding.ivEdit.setOnClickListener { onEditClick(currentNote) }
        holder.binding.ivDelete.setOnClickListener { onDeleteClick(currentNote) }
    }

    // Počet poznámek → RecyclerView ví, kolik řádků vykreslit
    override fun getItemCount(): Int = notes.size

    // Aktualizuje seznam poznámek a obnoví zobrazení
    fun submitList(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
