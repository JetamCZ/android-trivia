package eu.puhony.trivia.ui.screens.QuizDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.puhony.trivia.TriviaApplication
import eu.puhony.trivia.data.Repository
import eu.puhony.trivia.data.quiz.Quiz
import eu.puhony.trivia.data.quizResults.UserWithBestScore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizDetailViewModel(
    private val quizId: Int,
    private val repository: Repository
) : ViewModel() {
    private val _quiz = MutableStateFlow<Quiz?>(null)
    val quiz: StateFlow<Quiz?> = _quiz

    private val _bestScoreResults = MutableStateFlow<List<UserWithBestScore>>(emptyList())
    val bestScoreResults = _bestScoreResults

    init {
        viewModelScope.launch {
            _quiz.value = repository.getQuizById(quizId)
            _bestScoreResults.value = repository.getQuizBestResults(quizId)
        }
    }

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