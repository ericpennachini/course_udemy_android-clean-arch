package ar.com.udemy.exampleapp.app.framework.di

import ar.com.udemy.exampleapp.app.framework.ListViewModel
import ar.com.udemy.exampleapp.app.framework.NoteViewModel
import dagger.Component

@Component(
    modules = [
        ApplicationModule::class,
        RepositoryModule::class,
        UseCasesModule::class
    ]
)
interface ViewModelComponent {

    fun inject(noteViewModel: NoteViewModel)

    fun inject(listViewModel: ListViewModel)

}