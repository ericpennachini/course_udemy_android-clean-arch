package ar.com.udemy.exampleapp.app.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.com.udemy.exampleapp.core.data.Note

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    var title: String,

    var content: String,
    @ColumnInfo(name = "creation_time")
    var creationTime: Long,
    @ColumnInfo(name = "update_time")
    var updateTime: Long
) {

    companion object {
        fun fromNote(note: Note) = NoteEntity(
            id = note.id,
            title = note.title,
            content = note.content,
            creationTime = note.creationTime,
            updateTime = note.updateTime
        )
    }

    fun toNote() = Note(id, title, content, creationTime, updateTime)

}
