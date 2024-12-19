package eu.puhony.trivia.ui.screens.QuizDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.puhony.trivia.R
import eu.puhony.trivia.ui.components.LayoutColumn

@Composable
fun QuizDetailScreen(
    quizId: Int,
    viewModel: QuizDetailViewModel = viewModel(factory = QuizDetailViewModel.provideFactory(quizId)),
    onQuizStart: () -> Unit
) {
    val quiz by viewModel.quiz.collectAsState()

    LayoutColumn {
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "Ready to start quiz",
                fontSize = 30.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = quiz?.title ?: "",
                fontSize = 40.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.glow_pink)
            )
        }

        Text(text = quiz?.description ?: "", modifier = Modifier.padding(bottom = 16.dp))


        Button(
            onClick={onQuizStart()},
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.glow_pink)
            )
        ) {
            Text(text = "Start the quiz!")
        }
    }
}