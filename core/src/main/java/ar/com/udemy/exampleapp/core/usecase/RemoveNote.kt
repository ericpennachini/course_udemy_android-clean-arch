package ar.com.udemy.exampleapp.core.usecase

import ar.com.udemy.exampleapp.core.data.Note
import ar.com.udemy.exampleapp.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)

}