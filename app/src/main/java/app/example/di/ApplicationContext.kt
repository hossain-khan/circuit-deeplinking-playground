package app.example.di

import android.content.Context
import dev.zacsweers.metro.Qualifier

/** Qualifier to denote a `Context` that is specifically an Application context. */
@Qualifier 
data class ApplicationContext(val context: Context)
