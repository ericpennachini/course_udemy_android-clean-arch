package ar.com.udemy.exampleapp.app.framework

import ar.com.udemy.exampleapp.core.usecase.*

data class UseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote,
    val getWordCount: GetWordCount
)