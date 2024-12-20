package eu.puhony.trivia.ui.screens.resultScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.puhony.trivia.TriviaApplication
import eu.puhony.trivia.data.MyConfiguration
import eu.puhony.trivia.data.Repository
import eu.puhony.trivia.data.quiz.Quiz
import eu.puhony.trivia.data.quizResults.QuizResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResultScreenViewModel (
    private val quizId: Int,
    private val resultId: Int,
    private val repository: Repository
) : ViewModel() {
    private val _quiz = MutableStateFlow<Quiz?>(null)
    private val _results = MutableStateFlow<List<QuizResult>?>(null)
    val quiz: StateFlow<Quiz?> = _quiz
    val results: StateFlow<List<QuizResult>?> = _results

    init {
        viewModelScope.launch {
            _quiz.value = repository.getQuizById(quizId)
            _results.value = repository.getUserResults(quizId, MyConfiguration.loggedInUser?.id ?: 0)
        }
    }

    companion object {
        fun provideFactory(quizId: Int, resultId: Int): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TriviaApplication

                ResultScreenViewModel(
                    repository = application.repository,
                    quizId = quizId,
                    resultId = resultId
                )
            }
        }
    }
}