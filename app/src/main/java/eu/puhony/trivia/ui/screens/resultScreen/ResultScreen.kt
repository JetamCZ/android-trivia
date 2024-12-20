package eu.puhony.trivia.ui.screens.resultScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import eu.puhony.trivia.R
import eu.puhony.trivia.data.MyConfiguration
import eu.puhony.trivia.ui.components.LayoutColumn

@Composable
fun ResultScreen(
    resultId: Int,
    onContinue: () -> Unit,
    viewModel: ResultScreenViewModel,
    onBackPressed: () -> Unit
) {
    BackHandler(onBack = onBackPressed)

    val quiz by viewModel.quiz.collectAsState()
    val results by viewModel.results.collectAsState()

    LayoutColumn {
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = "Contgrats ${MyConfiguration.loggedInUser?.username}",
                fontSize = 30.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "you finished",
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

        if (results != null) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(results!!) { result ->
                    val color = if (result.id == resultId) {
                        colorResource(id = R.color.glow_pink)
                    } else {
                        MaterialTheme.colorScheme.surface
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = color
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp), // Optional padding for better spacing
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = formatTimestamp(result.completedAt))
                            Text(text = "${result.score} points")
                        }
                    }
                }
            }
        }

        Button(
            onClick={onContinue()},
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.glow_pink)
            )
        ) {
            Text(text = "Go back to detail")
        }
    }
}