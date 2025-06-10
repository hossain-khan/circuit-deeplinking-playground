package app.example.di

import android.app.Activity
import android.content.Context
import app.example.MainActivity
import com.slack.circuit.foundation.Circuit
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@DependencyGraph(AppScope::class)
interface AppComponent {
  val circuit: Circuit
  
  /**
   * A multibinding map of activity classes to their providers accessible for
   * [ComposeAppComponentFactory].
   */
  @Multibinds 
  val activityProviders: Map<KClass<out Activity>, Provider<Activity>>

  companion object {
    fun create(context: Context): AppComponent = 
      AppComponentImpl.create(ApplicationContext(context))
  }
}
