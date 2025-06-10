package app.example.di

import app.example.data.ExampleEmailValidator
import dev.zacsweers.metro.Contribute

// Example of a Metro module that provides dependencies for the app.
@Contribute(AppScope::class)
object ExampleAppModule {
  fun provideEmailRepository(): ExampleEmailValidator = ExampleEmailValidator()
}
