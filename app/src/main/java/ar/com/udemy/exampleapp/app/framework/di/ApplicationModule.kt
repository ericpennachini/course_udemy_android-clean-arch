package ar.com.udemy.exampleapp.app.framework.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: Application) {

    @Provides
    fun provideApp() = app

}