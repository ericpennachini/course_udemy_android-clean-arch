package ar.com.udemy.exampleapp.app.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.udemy.exampleapp.R
import ar.com.udemy.exampleapp.app.framework.ListViewModel
import ar.com.udemy.exampleapp.core.data.Note
import ar.com.udemy.exampleapp.core.data.Resource
import ar.com.udemy.exampleapp.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val notesListAdapter = NotesListAdapter(mutableListOf())

    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViews()
        configureViewModel()
    }

    private fun configureViews() {
        binding.rvNotesContainer.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter
        }

        binding.fabAddNewNote.setOnClickListener {
            goToNoteDetails()
        }
    }

    private fun configureViewModel() {
        viewModel.notesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> onLoading()
                is Resource.Success -> onSuccess(it.data ?: listOf())
                is Resource.Error -> onError(it.message.orEmpty())
            }
        }
    }

    private fun onLoading() = binding.pbLoading.show()

    private fun onSuccess(notes: List<Note>) {
        binding.pbLoading.hide()
        notesListAdapter.updateNotes(
            notes.sortedByDescending { it.updateTime }.toMutableList()
        )
    }

    private fun onError(errorMessage: String) {
        binding.pbLoading.hide()
        Snackbar.make(
            binding.root,
            getString(R.string.generic_error_message),
            Snackbar.LENGTH_LONG
        ).setAction("Retry") { viewModel.getAllNotes() }.show()
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val action = ListFragmentDirections.actionListFragmentToNoteFragment(id)
        Navigation.findNavController(binding.root).navigate(action)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNotes()
    }

}