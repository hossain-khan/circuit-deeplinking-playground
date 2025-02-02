package app.example

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.example.circuit.DetailScreen
import app.example.circuit.InboxScreen
import app.example.deeplinking.DEEP_LINK_HOST_VIEW_EMAIL
import app.example.deeplinking.getIdFromPath
import app.example.di.ActivityKey
import app.example.di.AppScope
import app.example.ui.theme.ComposeAppTheme
import com.slack.circuit.backstack.rememberSaveableBackStack
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.gesturenavigation.GestureNavigationDecoration
import com.squareup.anvil.annotations.ContributesMultibinding
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

@ContributesMultibinding(AppScope::class, boundType = Activity::class)
@ActivityKey(MainActivity::class)
class MainActivity
    @Inject
    constructor(
        private val circuit: Circuit,
    ) : ComponentActivity() {
        private lateinit var navigator: Navigator

        override fun onCreate(savedInstanceState: Bundle?) {
            enableEdgeToEdge()
            super.onCreate(savedInstanceState)

            val action: String? = intent?.action
            val data: Uri? = intent?.data
            Log.d("App", "onCreate action: $action, data: $data, savedInstanceState: $savedInstanceState")

            setContent {
                ComposeAppTheme {
                    val stack: ImmutableList<Screen> = parseDeepLink(intent) ?: listOf(InboxScreen).toImmutableList()
                    // See https://slackhq.github.io/circuit/navigation/
                    val backStack = rememberSaveableBackStack(stack)
                    val navigator: Navigator = rememberCircuitNavigator(backStack)

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

        private fun parseDeepLink(intent: Intent): ImmutableList<Screen>? {
            val dataUri: Uri = intent.data ?: return null

            when (dataUri.host) {
                DEEP_LINK_HOST_VIEW_EMAIL -> {
                    val emailId = getIdFromPath(dataUri)
                    if (emailId != null) {
                        return listOf(DetailScreen(emailId)).toImmutableList()
                    }
                }
            }
            return null
        }
    }
