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
import app.example.circuit.DraftNewEmailScreen
import app.example.circuit.InboxScreen
import app.example.deeplinking.DEEP_LINK_EMAIL_ID_QUERY_PARAM
import app.example.deeplinking.DEEP_LINK_PATH_DRAFT_NEW_EMAIL
import app.example.deeplinking.DEEP_LINK_PATH_INBOX
import app.example.deeplinking.DEEP_LINK_PATH_VIEW_EMAIL
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

            val screens = mutableListOf<Screen>()
            dataUri.pathSegments.filter { it.isNotBlank() }.forEach { pathSegment ->
                when (pathSegment) {
                    DEEP_LINK_PATH_INBOX -> {
                        screens.add(InboxScreen)
                    }
                    DEEP_LINK_PATH_VIEW_EMAIL -> {
                        // For email screen, we require the email id from query parameter
                        val emailId = dataUri.getQueryParameter(DEEP_LINK_EMAIL_ID_QUERY_PARAM)
                        if (emailId != null) {
                            screens.add(DetailScreen(emailId))
                        }
                    }
                    DEEP_LINK_PATH_DRAFT_NEW_EMAIL -> {
                        screens.add(DraftNewEmailScreen)
                    }
                    else -> Log.d("MainActivity", "Unknown path segment: $pathSegment")
                }
            }

            return if (screens.isNotEmpty()) {
                screens.toImmutableList()
            } else {
                null
            }
        }
    }
