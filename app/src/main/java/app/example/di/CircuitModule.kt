package app.example.di

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import dev.zacsweers.metro.Contribute
import me.tatarka.inject.annotations.Provides

/**
 * Metro module that provides dependencies for the Circuit framework.
 */
@Contribute(AppScope::class)
object CircuitModule {
  /**
   * Provides a singleton instance of Circuit with presenter and ui configured.
   */
  @Provides
  fun provideCircuit(
    presenterFactories: Set<Presenter.Factory>,
    uiFactories: Set<Ui.Factory>,
  ): Circuit =
    Circuit
      .Builder()
      .addPresenterFactories(presenterFactories)
      .addUiFactories(uiFactories)
      .build()
}
