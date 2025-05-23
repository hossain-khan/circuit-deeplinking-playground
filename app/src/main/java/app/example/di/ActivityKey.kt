package app.example.di

import android.app.Activity
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

/**
 * A Metro multi-binding key used for registering a [Activity] into the top level dependency graph.
 */
@MapKey annotation class ActivityKey(
  val value: KClass<out Activity>,
)
