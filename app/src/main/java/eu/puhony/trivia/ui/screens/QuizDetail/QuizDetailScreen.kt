package eu.puhony.trivia.ui.screens.QuizDetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.puhony.trivia.ui.components.BigHeading
import eu.puhony.trivia.ui.components.LayoutColumn

@Composable
fun QuizDetailScreen(
    quizId: Int,
    viewModel: QuizDetailViewModel = viewModel(factory = QuizDetailViewModel.provideFactory(quizId))
) {
    val quiz by viewModel.quiz.collectAsState()

    LayoutColumn {
        BigHeading("Ready to start quiz `${quiz?.title}`?")
        Text(text = "Number of questions: ${quiz?.amount}")
        Text(text = "Id: ${quizId}")
    }
}