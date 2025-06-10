package app.example.di

import android.app.Activity
import dev.zacsweers.metro.MapKey
import kotlin.reflect.KClass

/** Key for binding activities in multibindings. */
@MapKey
annotation class ActivityKey(val value: KClass<out Activity>)