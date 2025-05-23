package app.example.di

import android.app.Activity
import android.content.Context
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import kotlin.reflect.KClass

@DependencyGraph(AppScope::class)
interface AppGraph {
  val activityProviders: Map<KClass<out Activity>, Provider<Activity>>

  @DependencyGraph.Factory
  interface Factory {
    fun create(@Provides @ApplicationContext context: Context): AppGraph
  }

  companion object {
    fun create(context: Context): AppGraph = dev.zacsweers.metro.createGraphFactory<Factory>().create(context)
  }
}
