package eu.puhony.trivia.ui.screens.quiz

import BigLoading
import ErrorScreen
import android.text.Html
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.puhony.trivia.R
import eu.puhony.trivia.ui.components.BigHeading

@Composable
fun QuizScreen(
    quizId: Int,
    onFinish: () -> Unit,
    viewModel: QuizScreenViewModel = viewModel(factory = QuizScreenViewModel.provideFactory(quizId, onFinish)),
) {
    val state = viewModel.uiState.collectAsState()
    val quiz by viewModel.quiz.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = quiz?.title ?: "",
            fontSize = 30.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            color = colorResource(id = R.color.glow_pink)
        )

        if (state.value.isLoading) {
            BigLoading()
        } else if (state.value.error != null) {
            ErrorScreen(
                errorMessage = state.value.error ?: "",
                onRetry = { viewModel.reload() }
            )
        } else {

            // Progress and Score
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Question ${state.value.currentQuestionIndex + 1}/${state.value.totalQuestions}")
                Text(text = "Score: ${state.value.score}")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Question
            Text(
                text = Html.fromHtml(
                    state.value.currentQuestion?.question ?: "",
                    Html.FROM_HTML_MODE_COMPACT
                ).toString(),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Answer Options
            state.value.currentQuestion?.let { question ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    question.answers.forEach { answer ->
                        OutlinedButton(
                            onClick = { viewModel.submitAnswer(answer) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(88.dp)
                                .padding(bottom = 16.dp),
                            border = BorderStroke(1.dp, colorResource(id = R.color.glow_pink))
                        ) {
                            Text(text = answer, color = colorResource(id = R.color.glow_pink))
                        }
                    }
                }
            }
        }
    }
}