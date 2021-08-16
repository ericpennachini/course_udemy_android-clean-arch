package ar.com.udemy.exampleapp.app.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ar.com.udemy.exampleapp.core.data.Note
import ar.com.udemy.exampleapp.core.repository.NoteRepository
import ar.com.udemy.exampleapp.core.usecase.AddNote
import ar.com.udemy.exampleapp.core.usecase.GetAllNotes
import ar.com.udemy.exampleapp.core.usecase.GetNote
import ar.com.udemy.exampleapp.core.usecase.RemoveNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NoteViewModel(application: Application): AndroidViewModel(application) {

    val repository = NoteRepository(RoomNoteDataSource(application))

    val useCases = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository)
    )

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    fun saveNote(note: Note) {
        viewModelScope.launch {
            try {
                useCases.addNote(note)
                _saved.value = true
            } catch (ex: Exception) {
                _saved.value = false
            }
        }
    }

}