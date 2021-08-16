package ar.com.udemy.exampleapp.app.framework.di

import android.app.Application
import ar.com.udemy.exampleapp.app.framework.RoomNoteDataSource
import ar.com.udemy.exampleapp.core.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))

}