package eu.puhony.trivia.ui.screens.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import eu.puhony.trivia.R
import eu.puhony.trivia.data.quiz.Quiz
import eu.puhony.trivia.ui.components.BigHeading
import eu.puhony.trivia.ui.components.LayoutColumn

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    onQuizSelect: (quizId: Int) -> Unit,
    viewModel: ListScreenViewModel = viewModel(factory = ListScreenViewModel.Factory),
    onBackPressed: () -> Unit
) {
    BackHandler(onBack = onBackPressed)

    val quizes by viewModel.allQuizes.collectAsState(initial = emptyList())

    LayoutColumn(modifier) {
        BigHeading("Select trivia")

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(quizes.size) { index ->
                QuizItem(quiz = quizes[index], onClick = {
                    onQuizSelect(it)
                })
            }
        }
    }
}

@Composable
fun QuizItem(quiz: Quiz, onClick: (id: Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(quiz.id)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.glow_pink))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = quiz.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Category: ${quiz.description}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}