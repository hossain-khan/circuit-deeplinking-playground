package app.example.di

import dev.zacsweers.metro.Scope
import javax.inject.Scope

/**
 * App scope for dependency injection.
 *
 * Metro scope annotation for the application-level scope.
 * The @Scope annotation from javax.inject is kept for compatibility with Dagger during migration.
 */
@Scope
@Scope(AppScope::class)
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope
