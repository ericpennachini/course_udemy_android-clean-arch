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
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteViewModel(application: Application): AndroidViewModel(application) {

    @Inject
    lateinit var useCases: UseCases

    private val _currentNoteLiveData = MutableLiveData<Resource<Note?>>()
    val currentNoteLiveData: LiveData<Resource<Note?>> = _currentNoteLiveData

    private val _saved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean> = _saved

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(application))
            .build()
            .inject(this)
    }

    fun getNote(id: Long) {
        viewModelScope.launch {
            try {
                _currentNoteLiveData.value = Resource.Loading()
                val note = useCases.getNote(id)
                _currentNoteLiveData.value = Resource.Success(note)
            } catch (ex: Exception) {
                _currentNoteLiveData.value = Resource.Error(ex)
            }
        }
    }

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

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            try {
                useCases.removeNote(note)
                _saved.value = true
            } catch (ex: Exception) {
                _saved.value = false
            }
        }
    }

}