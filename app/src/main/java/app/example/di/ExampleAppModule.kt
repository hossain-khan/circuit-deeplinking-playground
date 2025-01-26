package app.example.di

import app.example.data.ExampleEmailValidator
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

// Example of a Dagger module that provides dependencies for the app.
@ContributesTo(AppScope::class)
@Module
class ExampleAppModule {
    @Provides
    fun provideEmailRepository(): ExampleEmailValidator = ExampleEmailValidator()
}
