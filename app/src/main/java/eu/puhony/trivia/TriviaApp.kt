package eu.puhony.trivia

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import eu.puhony.trivia.ui.screens.QuizDetail.QuizDetailScreen
import kotlinx.serialization.Serializable
import eu.puhony.trivia.ui.screens.login.LoginScreen
import eu.puhony.trivia.ui.screens.quiz.QuizScreen
import eu.puhony.trivia.ui.screens.list.ListScreen
import eu.puhony.trivia.ui.screens.quiz.QuestionItem
import eu.puhony.trivia.ui.screens.resultScreen.ResultScreen
import kotlinx.serialization.Contextual

@Composable
fun TriviaApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LoginScreenUrl,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        )
        {
            composable<LoginScreenUrl> {
                LoginScreen(
                    onLogin = { navController.navigate(ListScreenUrl) }
                )
            }

            composable<QuizPlayUrl> {
                val args = it.toRoute<QuizPlayUrl>()

                QuizScreen(
                    quizId = args.quizId,
                    onFinish = { resultId ->
                        navController.navigate(
                            QuizResultsScreen(
                                quizId = args.quizId,
                                resultId = resultId
                            )
                        )
                    }
                )
            }

            composable<QuizDetailUrl> {
                val args = it.toRoute<QuizDetailUrl>()
                QuizDetailScreen(
                    quizId = args.quizId,
                    onQuizStart = {
                        navController.navigate(
                            QuizPlayUrl(
                                quizId = args.quizId
                            )
                        )
                    }
                )
            }

            composable<ListScreenUrl> {
                ListScreen(
                    onQuizSelect = {
                        navController.navigate(QuizDetailUrl(quizId = it))
                    }
                )
            }

            composable<QuizResultsScreen> {
                val args = it.toRoute<QuizResultsScreen>()
                ResultScreen(
                    quizId = args.quizId,
                    resultId = args.resultId,
                    onContinue = {
                        navController.navigate(QuizDetailUrl(
                            quizId = args.quizId
                        ))
                    }
                )
            }

        }
    }
}

@Serializable
object LoginScreenUrl

@Serializable
data class QuizDetailUrl(
    val quizId: Int
)

@Serializable
data class QuizPlayUrl(
    val quizId: Int
)

@Serializable
object ListScreenUrl

@Serializable
data class QuizResultsScreen(
    val quizId: Int,
    val resultId: Int
)