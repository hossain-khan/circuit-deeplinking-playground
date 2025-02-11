package app.example

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.example.circuit.DetailScreen
import app.example.circuit.DraftNewEmailScreen
import app.example.circuit.InboxScreen
import app.example.di.ActivityKey
import app.example.di.AppScope
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.gesturenavigation.GestureNavigationDecoration
import com.squareup.anvil.annotations.ContributesMultibinding
import javax.inject.Inject

@ContributesMultibinding(AppScope::class, boundType = Activity::class)
@ActivityKey(MainActivity::class)
class MainActivity
  @Inject
  constructor(
    private val circuit: Circuit,
  ) : ComponentActivity() {
    private var stackedScreens by mutableStateOf<List<Screen>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
      enableEdgeToEdge()
      super.onCreate(savedInstanceState)

      val action: String? = intent?.action
      val data: Uri? = intent?.data
      Log.d("App", "onCreate action: $action, data: $data, savedInstanceState: $savedInstanceState")

      setContent {
        MaterialTheme {
          stackedScreens = parseDeepLink(intent) ?: listOf(InboxScreen)
          // var stackedScreens by remember { mutableStateOf(screens) }
          // See https://slackhq.github.io/circuit/navigation/
          val backStack = rememberSaveableBackStack(stackedScreens)
          val navigator = rememberCircuitNavigator(backStack)

          // See https://slackhq.github.io/circuit/circuit-content/
          CircuitCompositionLocals(circuit) {
            NavigableCircuitContent(
              navigator = navigator,
              backStack = backStack,
              decoration =
                GestureNavigationDecoration {
                  navigator.pop()
                },
            )
          }
        }
      }
    }

    override fun onNewIntent(intent: Intent) {
      super.onNewIntent(intent)
      Log.d("App", "onNewIntent received: $intent")
      stackedScreens = parseDeepLink(intent) ?: listOf(InboxScreen)
    }

    /**
     * Parses the deep link from the given [Intent.getData] and returns a list of screens to navigate to.
     */
    private fun parseDeepLink(intent: Intent): List<Screen>? {
      val dataUri = intent.data ?: return null
      val screens = mutableListOf<Screen>()

      dataUri.pathSegments.filter { it.isNotBlank() }.forEach { pathSegment ->
        when (pathSegment) {
          "inbox" -> screens.add(InboxScreen)
          "view_email" ->
            dataUri.getQueryParameter("emailId")?.let {
              screens.add(DetailScreen(it))
            }
          "new_email" -> screens.add(DraftNewEmailScreen)
          else -> Log.d("App", "Unknown path segment: $pathSegment")
        }
      }

      return screens.takeIf { it.isNotEmpty() }
    }
  }
