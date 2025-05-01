package app.example.di

import app.example.data.ExampleEmailValidator
import dev.zacsweers.metro.Module
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Single
import dev.zacsweers.metro.interop.dagger.compat.DaggerInterop

/**
 * Module that provides dependencies for the app.
 */
@Module
@DaggerInterop
class ExampleAppModule {
  @Provides
  @Single(AppScope::class)
  fun provideEmailRepository(): ExampleEmailValidator = ExampleEmailValidator()
}
