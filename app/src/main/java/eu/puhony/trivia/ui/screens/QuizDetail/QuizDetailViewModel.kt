package eu.puhony.trivia.ui.screens.QuizDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.puhony.trivia.TriviaApplication
import eu.puhony.trivia.data.Repository
import eu.puhony.trivia.data.quiz.Quiz

class QuizDetailViewModel (
    quizId: Int,
    private val repository: Repository
) : ViewModel() {
    val quiz: LiveData<Quiz> = repository.getQuizById(quizId)


    companion object {
        fun provideFactory(quizId: Int): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TriviaApplication

                QuizDetailViewModel(
                    repository = application.repository,
                    quizId = quizId
                )
            }
        }
    }
}