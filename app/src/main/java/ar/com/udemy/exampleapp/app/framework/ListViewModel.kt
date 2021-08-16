package ar.com.udemy.exampleapp.app.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ar.com.udemy.exampleapp.core.data.Note
import ar.com.udemy.exampleapp.core.data.Resource
import ar.com.udemy.exampleapp.core.repository.NoteRepository
import ar.com.udemy.exampleapp.core.usecase.AddNote
import ar.com.udemy.exampleapp.core.usecase.GetAllNotes
import ar.com.udemy.exampleapp.core.usecase.GetNote
import ar.com.udemy.exampleapp.core.usecase.RemoveNote
import kotlinx.coroutines.launch
import kotlin.Exception

class ListViewModel(
    application: Application
) : AndroidViewModel(application) {

    val repository = NoteRepository(RoomNoteDataSource(application))

    val useCases = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository)
    )

    private val _notesLiveData = MutableLiveData<Resource<List<Note>>>()
    val notesLiveData: LiveData<Resource<List<Note>>> = _notesLiveData

    fun getAllNotes() {
        viewModelScope.launch {
            try {
                _notesLiveData.value = Resource.Loading()
                val notes = useCases.getAllNotes()
                _notesLiveData.value = Resource.Success(notes)
            } catch (ex: Exception) {
                _notesLiveData.value = Resource.Error(ex)
            }
        }
    }

}