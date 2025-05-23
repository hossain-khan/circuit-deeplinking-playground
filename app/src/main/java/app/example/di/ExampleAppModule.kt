package app.example.di

import app.example.data.ExampleEmailValidator
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

// Example of a Metro module that provides dependencies for the app.
@ContributesTo(AppScope::class)
interface ExampleAppModule {
  companion object {
    @Provides
    fun provideEmailRepository(): ExampleEmailValidator = ExampleEmailValidator()
  }
}
