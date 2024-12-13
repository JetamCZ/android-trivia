package eu.puhony.trivia

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import eu.puhony.trivia.ui.screens.login.LoginScreen
import eu.puhony.trivia.ui.screens.quiz.QuizScreen

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
            composable<HomeScreen> {
                Text(text = "Hello")
            }

            composable<LoginScreenUrl> {
                LoginScreen(
                    onLogin = {navController.navigate(QuizUrl)}
                )
            }

            composable<QuizUrl> {
                QuizScreen(

                )
            }

        }
    }
}


@Serializable
object HomeScreen

@Serializable
object LoginScreenUrl

@Serializable
object QuizUrl