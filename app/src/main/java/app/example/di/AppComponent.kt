package app.example.di

import android.app.Activity
import android.content.Context
import dev.zacsweers.metro.Component
import dev.zacsweers.metro.Module
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Single
import dev.zacsweers.metro.interop.dagger.compat.DaggerInterop
import javax.inject.Provider

@Component(
  modules = [ExampleAppModule::class, CircuitModule::class],
)
@Single(AppScope::class)
interface AppComponent {
  val activityProviders: Map<Class<out Activity>, @JvmSuppressWildcards Provider<Activity>>
  
  @DaggerInterop
  @Component.Factory
  interface Factory {
    fun create(
      @ApplicationContext context: Context,
    ): AppComponent
  }

  companion object {
    fun create(context: Context): AppComponent = MetroAppComponent.create(context)
  }
}

@Module
@DaggerInterop
object ContextModule {
  @Provides
  @ApplicationContext
  @Single(AppScope::class)
  fun provideContext(@ApplicationContext context: Context): Context = context
}
