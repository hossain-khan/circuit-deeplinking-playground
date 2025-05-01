package app.example.di

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.zacsweers.metro.Module
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Single
import dev.zacsweers.metro.interop.dagger.compat.DaggerInterop

/**
 * Module that provides dependencies for the Circuit framework.
 */
@Module
@DaggerInterop
interface CircuitModule {
  companion object {
    /**
     * Provides a singleton instance of Circuit with presenter and ui configured.
     */
    @Single(AppScope::class)
    @Provides
    fun provideCircuit(
      presenterFactories: @JvmSuppressWildcards Set<Presenter.Factory>,
      uiFactories: @JvmSuppressWildcards Set<Ui.Factory>,
    ): Circuit =
      Circuit
        .Builder()
        .addPresenterFactories(presenterFactories)
        .addUiFactories(uiFactories)
        .build()
  }
}
