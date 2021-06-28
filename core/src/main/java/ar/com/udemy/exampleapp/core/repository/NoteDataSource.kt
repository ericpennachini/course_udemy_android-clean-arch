package ar.com.udemy.exampleapp.core.repository

import ar.com.udemy.exampleapp.core.data.Note

interface NoteDataSource {
    suspend fun add(note: Note)
    suspend fun get(id: Long): Note?
    suspend fun getAll(): List<Note>
    suspend fun remove(note: Note)
}