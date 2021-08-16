package ar.com.udemy.exampleapp.app.framework

import android.content.Context
import ar.com.udemy.exampleapp.app.framework.db.DatabaseService
import ar.com.udemy.exampleapp.app.framework.db.NoteEntity
import ar.com.udemy.exampleapp.core.data.Note
import ar.com.udemy.exampleapp.core.repository.NoteDataSource

class RoomNoteDataSource(context: Context) : NoteDataSource {

    private val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll() = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))

}