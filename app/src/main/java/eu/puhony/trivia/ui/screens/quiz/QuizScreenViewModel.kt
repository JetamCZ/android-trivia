package eu.puhony.trivia.ui.screens.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import eu.puhony.trivia.TriviaApplication
import eu.puhony.trivia.api.Question
import eu.puhony.trivia.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,

    val totalQuestions: Int = 0,
    val score: Int = 0,
    val currentQuestion: QuestionUiModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class QuestionUiModel(
    val question: String,
    val correctAnswer: String,
    val answers: List<String>
)

class QuizScreenViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _quizState = MutableStateFlow(QuizState())
    val uiState: StateFlow<QuizState> = _quizState

    private fun loadQuestion(index: Int) {
        if(uiState.value.questions.count() - 1 < index) return
        if(index < 0) return

        val current : Question = uiState.value.questions.get(index)
        val answers : List<String> = current.incorrect_answers + current.correct_answer

        _quizState.update { newState -> newState.copy(
            currentQuestionIndex = index,
            currentQuestion = QuestionUiModel(
                question = current.question,
                correctAnswer = current.correct_answer,
                answers = answers.shuffled()
            ),
        )}
    }

    fun reload() {
        if(uiState.value.isLoading) return

        viewModelScope.launch {
            Log.d("LOADING", "QUESTIONS loading!")

            _quizState.update { newState ->
                newState.copy(
                    isLoading = true
                )
            }

            val result = repository.fetchQuestions(amount = 10, category = null, difficulty = null, type = null)

            Log.d("LOADING", "QUESTIONS loaded - ${result.isSuccess}!")

            _quizState.update { newState ->
                if(result.isSuccess) {
                    val questions: List<Question> = result.getOrDefault(emptyList())

                    newState.copy(
                        isLoading = false,
                        questions = questions,
                        currentQuestionIndex = 0,
                        totalQuestions = questions.count(),
                        score = 0
                    )
                } else {
                    newState.copy(
                        isLoading = false,
                        error = "Nastala chyba při načítání..."
                    )
                }

            }

            loadQuestion(0)
        }
    }

    init {
        reload()
    }

    fun submitAnswer(answer: String) {
        Log.d("QUIZ","LOG answer")

        if (answer == uiState.value.currentQuestion?.correctAnswer) {
            _quizState.update { newState -> newState.copy(
                score = newState.score + 1
            )}

            //Play some cool sound
        } else {
            //Play some less-cool sound
        }

        loadQuestion(uiState.value.currentQuestionIndex + 1)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TriviaApplication

                QuizScreenViewModel(
                    repository = application.repository
                )
            }
        }
    }
}

