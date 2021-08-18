package ar.com.udemy.exampleapp.app.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ar.com.udemy.exampleapp.app.framework.di.ApplicationModule
import ar.com.udemy.exampleapp.app.framework.di.DaggerViewModelComponent
import ar.com.udemy.exampleapp.core.data.Note
import ar.com.udemy.exampleapp.core.data.Resource
import ar.com.udemy.exampleapp.core.repository.NoteRepository
import ar.com.udemy.exampleapp.core.usecase.AddNote
import ar.com.udemy.exampleapp.core.usecase.GetAllNotes
import ar.com.udemy.exampleapp.core.usecase.GetNote
import ar.com.udemy.exampleapp.core.usecase.RemoveNote
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

class ListViewModel(
    application: Application
) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases: UseCases

    private val _notesLiveData = MutableLiveData<Resource<List<Note>>>()
    val notesLiveData: LiveData<Resource<List<Note>>> = _notesLiveData

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(application))
            .build()
            .inject(this)
    }

    fun getAllNotes() {
        viewModelScope.launch {
            try {
                _notesLiveData.value = Resource.Loading()
                val notes = useCases.getAllNotes()
                notes.forEach { it.wordCount = useCases.getWordCount(it) }
                _notesLiveData.value = Resource.Success(notes)
            } catch (ex: Exception) {
                _notesLiveData.value = Resource.Error(ex)
            }
        }
    }

}