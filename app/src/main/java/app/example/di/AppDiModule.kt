package app.example.di

import android.content.Context
import app.example.data.ExampleEmailRepository
import app.example.data.ExampleEmailRepositoryImpl
import app.example.data.ExampleEmailValidator
import dev.zacsweers.metro.Contribute
import me.tatarka.inject.annotations.Provides

/**
 * Consolidated Metro DI module for the app.
 */
@Contribute(AppScope::class)
interface AppDiModule {
  
  fun ExampleEmailRepositoryImpl.bind(): ExampleEmailRepository
  
  @Provides
  fun provideEmailValidator(): ExampleEmailValidator = ExampleEmailValidator()
}