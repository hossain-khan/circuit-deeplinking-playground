package app.example.di

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.annotation.Keep
import androidx.core.app.AppComponentFactory
import app.example.ComposeApp
import app.example.MainActivity

/**
 * Custom implementation of [AppComponentFactory] used to inject Android components
 * (specifically Activities) via Metro/kotlin-inject using constructor injection. This factory
 * allows the Android system to delegate activity instantiation to our DI component,
 * enabling constructor injection instead of field injection.
 *
 * This class is referenced in the `AndroidManifest` within the `<application>` tag.
 *
 * Usage:
 * Add the following to your AndroidManifest.xml:
 *
 * ```xml
 * <application
 *     android:appComponentFactory=".di.ComposeAppComponentFactory"
 *     ... />
 * ```
 */
@Keep
class ComposeAppComponentFactory : AppComponentFactory() {
  /**
   * Called by the Android system to instantiate an activity. This method checks if the
   * activity can be provided by the Metro component. If the component can provide
   * the activity, it returns the injected instance; otherwise, it falls back to the
   * default system behavior.
   */
  override fun instantiateActivityCompat(
    classLoader: ClassLoader,
    className: String,
    intent: Intent?,
  ): Activity {
    return when (className) {
      MainActivity::class.java.name -> {
        val circuit = appComponent?.circuit
        if (circuit != null) {
          MainActivity(circuit)
        } else {
          super.instantiateActivityCompat(classLoader, className, intent)
        }
      }
      else -> super.instantiateActivityCompat(classLoader, className, intent)
    }
  }

  /**
   * Called by the system to instantiate the application. This method initializes the
   * Metro app component and stores it for later use.
   */
  override fun instantiateApplicationCompat(
    classLoader: ClassLoader,
    className: String,
  ): Application {
    val app = super.instantiateApplicationCompat(classLoader, className)
    // Retrieve the Metro app component from the application
    appComponent = (app as ComposeApp).appComponent()
    return app
  }

  /**
   * Companion object to store the app component reference.
   */
  companion object {
    private var appComponent: AppComponent? = null
  }
}
