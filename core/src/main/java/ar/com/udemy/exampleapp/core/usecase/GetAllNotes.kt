package ar.com.udemy.exampleapp.core.usecase

import ar.com.udemy.exampleapp.core.repository.NoteRepository

class GetAllNotes(private val noteRepository: NoteRepository) {

    suspend operator fun invoke() = noteRepository.getAllNotes()

}