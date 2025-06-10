package app.example.di

import android.app.Activity
import android.content.Context
import app.example.MainActivity
import com.slack.circuit.foundation.Circuit
import dev.zacsweers.metro.MetroComponent
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Scope

@MetroComponent
@Component
@AppScope
abstract class AppComponent(
  @get:Provides @ApplicationContext val context: Context,
) {
  abstract val circuit: Circuit

  companion object {
    fun create(context: Context): AppComponent = AppComponent::create(context)
  }
}
