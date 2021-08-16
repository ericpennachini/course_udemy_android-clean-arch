package ar.com.udemy.exampleapp.app.framework.di

import ar.com.udemy.exampleapp.app.framework.UseCases
import ar.com.udemy.exampleapp.core.repository.NoteRepository
import ar.com.udemy.exampleapp.core.usecase.AddNote
import ar.com.udemy.exampleapp.core.usecase.GetAllNotes
import ar.com.udemy.exampleapp.core.usecase.GetNote
import ar.com.udemy.exampleapp.core.usecase.RemoveNote
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun provideUseCases(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository)
    )

}