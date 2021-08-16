package ar.com.udemy.exampleapp.app.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.com.udemy.exampleapp.core.data.Note
import ar.com.udemy.exampleapp.databinding.ListItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NotesListAdapter(
    private val notes: MutableList<Note>,
    private val action: ListAction
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateNotes(newNotes: MutableList<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        ListItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size

    inner class NoteViewHolder(private val binding: ListItemNoteBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(note: Note) {
            binding.tvNoteId.text = note.id.toString()
            binding.tvNoteTitlePreview.text = note.title
            binding.tvNoteContentPreview.text = note.content
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT).format(Date(note.updateTime))
            binding.tvLastUpdatedDate.text = "Last updated: $date"
            binding.root.setOnClickListener {
                action.onClick(note.id)
            }
        }
    }

}