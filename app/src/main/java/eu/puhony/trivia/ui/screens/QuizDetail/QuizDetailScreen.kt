package eu.puhony.trivia.ui.screens.QuizDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import eu.puhony.trivia.R
import eu.puhony.trivia.ui.components.LayoutColumn

@Composable
fun QuizDetailScreen(
    quizId: Int,
    viewModel: QuizDetailViewModel = viewModel(factory = QuizDetailViewModel.provideFactory(quizId)),
    onQuizStart: () -> Unit,
    onBackPressed: () -> Unit
) {
    BackHandler(onBack = onBackPressed)

    val quiz by viewModel.quiz.collectAsState()
    val bestScoreResults by viewModel.bestScoreResults.collectAsState()

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

        if (bestScoreResults.size > 0) {
            Text(
                text = "Best scores",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 20.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(bestScoreResults) { user ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter("https://robohash.org/${user.user.username}"),
                                contentDescription = "User Avatar",
                                modifier = Modifier.size(50.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                            Text(
                                text = user.user.username,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )

                            Text(
                                text = "${user.bestScore} points",
                                fontSize = 21.sp,
                                textAlign = TextAlign.Right,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = { onQuizStart() },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.glow_pink)
            )
        ) {
            Text(text = "Start the quiz!")
        }
    }
}