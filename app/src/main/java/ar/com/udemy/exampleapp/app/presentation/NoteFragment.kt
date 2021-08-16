package ar.com.udemy.exampleapp.app.presentation

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import ar.com.udemy.exampleapp.R
import ar.com.udemy.exampleapp.app.framework.NoteViewModel
import ar.com.udemy.exampleapp.core.data.Note
import ar.com.udemy.exampleapp.core.data.Resource
import ar.com.udemy.exampleapp.databinding.FragmentNoteBinding
import com.google.android.material.snackbar.Snackbar

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    private val viewModel: NoteViewModel by viewModels()

    private var currentNote = Note(
        id = 0L, title = "", content = "", creationTime = 0L, updateTime = 0L
    )

    private var noteId = 0L

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

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) viewModel.getNote(noteId)

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
            viewModel.saveNote(currentNote)
        }
    }

    private fun navigateBack() = Navigation.findNavController(binding.root).popBackStack()

    private fun configureViewModel() {
        viewModel.saved.observe(viewLifecycleOwner, { isSaved ->
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

        viewModel.currentNoteLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data)
                is Resource.Error -> onError(it.message.orEmpty())
            }
        }
    }

    private fun onLoading() {
        // nothing
    }

    private fun onSuccess(note: Note?) {
       note?.let {
           currentNote = it
           binding.tiNoteTitle.setText(it.title, TextView.BufferType.EDITABLE)
           binding.tiNoteContent.setText(it.content, TextView.BufferType.EDITABLE)
       }
    }

    private fun onError(errorMessage: String) {
        Snackbar.make(
            binding.root,
            "${getString(R.string.generic_error_message)}: $errorMessage",
            Snackbar.LENGTH_LONG
        ).setAction("Retry") { it.hide() }.show()
    }

}