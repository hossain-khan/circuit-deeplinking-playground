package app.example.di

import android.app.Activity
import dev.zacsweers.metro.MapKey
import dev.zacsweers.metro.interop.dagger.compat.DaggerInterop
import kotlin.reflect.KClass

/**
 * A multi-binding key used for registering a [Activity] into the top level dependency graphs.
 */
@MapKey
@DaggerInterop
annotation class ActivityKey(
  val value: KClass<out Activity>,
)
