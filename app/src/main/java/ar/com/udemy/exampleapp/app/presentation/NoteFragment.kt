package ar.com.udemy.exampleapp.app.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ar.com.udemy.exampleapp.app.framework.NoteViewModel
import ar.com.udemy.exampleapp.core.data.Note
import ar.com.udemy.exampleapp.databinding.FragmentNoteBinding
import com.google.android.material.snackbar.Snackbar

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    private val noteViewModel: NoteViewModel by viewModels()

    private val currentNote = Note(
        id = 0L, title = "", content = "", creationTime = 0L, updateTime = 0L
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViews()
        configureViewModel()
    }

    private fun configureViews() {
        binding.btnSaveNote.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        if (binding.tiNoteTitle.text?.toString() != "" || binding.tiNoteContent.text?.toString() != "") {
            val time = System.currentTimeMillis()
            currentNote.title = binding.tiNoteTitle.text.toString()
            currentNote.content = binding.tiNoteContent.text.toString()
            currentNote.updateTime = time
            if (currentNote.id == 0L) currentNote.creationTime = time
            noteViewModel.saveNote(currentNote)
        }
    }

    private fun navigateBack() = Navigation.findNavController(binding.root).popBackStack()

    private fun configureViewModel() {
        noteViewModel.saved.observe(viewLifecycleOwner, { isSaved ->
            if (isSaved) {
                Snackbar.make(
                    binding.root,
                    "Note saved successfully",
                    Snackbar.LENGTH_SHORT
                ).show()
                navigateBack()
            } else {
                Snackbar.make(
                    binding.root,
                    "Something went wrong...",
                    Snackbar.LENGTH_LONG
                ).setAction("Retry") { saveNote() }.show()
            }
        })
    }

}