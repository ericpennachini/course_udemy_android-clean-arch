package ar.com.udemy.exampleapp.core.usecase

import ar.com.udemy.exampleapp.core.repository.NoteRepository

class GetNote(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(id: Long) = noteRepository.getNote(id)

}