package ar.com.udemy.exampleapp.app.framework

import ar.com.udemy.exampleapp.core.usecase.AddNote
import ar.com.udemy.exampleapp.core.usecase.GetAllNotes
import ar.com.udemy.exampleapp.core.usecase.GetNote
import ar.com.udemy.exampleapp.core.usecase.RemoveNote

data class UseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote
)