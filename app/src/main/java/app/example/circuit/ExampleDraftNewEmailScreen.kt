package app.example.circuit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.example.data.ExampleEmailRepository
import app.example.di.AppScope
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

@Parcelize
data object DraftNewEmailScreen : Screen {
    data class State(
        val recipients: String = "",
        val subject: String = "",
        val body: String = "",
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed class Event : CircuitUiEvent {
        data object SendEmailClicked : Event()

        data class RecipientsChanged(
            val recipients: String,
        ) : Event()

        data class SubjectChanged(
            val subject: String,
        ) : Event()

        data class BodyChanged(
            val body: String,
        ) : Event()
    }
}

class DraftNewEmailPresenter
    @AssistedInject
    constructor(
        @Assisted private val navigator: Navigator,
        private val emailRepository: ExampleEmailRepository,
    ) : Presenter<DraftNewEmailScreen.State> {
        @Composable
        override fun present(): DraftNewEmailScreen.State {
            val recipients = remember { mutableStateOf("") }
            val subject = remember { mutableStateOf("") }
            val body = remember { mutableStateOf("") }

            return DraftNewEmailScreen.State(
                recipients = recipients.value,
                subject = subject.value,
                body = body.value,
            ) { event ->
                when (event) {
                    is DraftNewEmailScreen.Event.RecipientsChanged -> recipients.value = event.recipients
                    is DraftNewEmailScreen.Event.SubjectChanged -> subject.value = event.subject
                    is DraftNewEmailScreen.Event.BodyChanged -> body.value = event.body
                    DraftNewEmailScreen.Event.SendEmailClicked -> {
                        val recipientList = recipients.value.split(",").map { it.trim() }
                        emailRepository.sendEmail(recipientList, subject.value, body.value)
                        navigator.pop()
                    }
                }
            }
        }

        @CircuitInject(DraftNewEmailScreen::class, AppScope::class)
        @AssistedFactory
        fun interface Factory {
            fun create(navigator: Navigator): DraftNewEmailPresenter
        }
    }

@CircuitInject(screen = DraftNewEmailScreen::class, scope = AppScope::class)
@Composable
fun DraftNewEmailContent(
    state: DraftNewEmailScreen.State,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier.padding(innerPadding).padding(16.dp)) {
            OutlinedTextField(
                value = state.recipients,
                onValueChange = { state.eventSink(DraftNewEmailScreen.Event.RecipientsChanged(it)) },
                label = { Text("Recipients (comma-separated)") },
                modifier = Modifier.fillMaxSize().padding(bottom = 8.dp),
            )
            OutlinedTextField(
                value = state.subject,
                onValueChange = { state.eventSink(DraftNewEmailScreen.Event.SubjectChanged(it)) },
                label = { Text("Subject") },
                modifier = Modifier.fillMaxSize().padding(bottom = 8.dp),
            )
            OutlinedTextField(
                value = state.body,
                onValueChange = { state.eventSink(DraftNewEmailScreen.Event.BodyChanged(it)) },
                label = { Text("Body") },
                modifier = Modifier.fillMaxSize().padding(bottom = 8.dp),
            )
            Button(
                onClick = { state.eventSink(DraftNewEmailScreen.Event.SendEmailClicked) },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Send Email")
            }
        }
    }
}
