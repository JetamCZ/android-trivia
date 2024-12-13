package eu.puhony.trivia.ui.screens.quiz

import android.text.Html
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuizScreen(
    viewModel: QuizScreenViewModel = viewModel(factory = QuizScreenViewModel.Factory),
) {
    val state = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(state.value.isLoading) {
            Text("Loading...")
        } else if (state.value.error != null) {
            Text(text = state.value.error ?: "")
            Button(onClick = {viewModel.reload()}) {
                Text("RELOAD")
            }
        }

        // Progress and Score
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Question ${state.value.currentQuestionIndex+1}/${state.value.totalQuestions}")
            Text(text ="Score: ${state.value.score}")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Question
        Text(
            text = Html.fromHtml(state.value.currentQuestion?.question ?: "", Html.FROM_HTML_MODE_COMPACT).toString(),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Answer Options
        state.value.currentQuestion?.let { question ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                question.answers.forEach { answer ->
                    Button(
                        onClick = { viewModel.submitAnswer(answer) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(answer)
                    }
                }
            }
        }
    }
}