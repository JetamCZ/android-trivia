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
import eu.puhony.trivia.data.MyConfiguration
import eu.puhony.trivia.data.Repository
import eu.puhony.trivia.data.quiz.Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizState(
    val questions: List<QuestionItem> = emptyList(),
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
    private val repository: Repository,
    private val quizId: Int,
    private val onFinish: (resultId: Int) -> Unit,
) : ViewModel() {
    private val _quiz = MutableStateFlow<Quiz?>(null)
    val quiz: StateFlow<Quiz?> = _quiz

    private val _quizState = MutableStateFlow(QuizState())
    val uiState: StateFlow<QuizState> = _quizState


    private fun loadQuestion(index: Int) {
        if (_quizState.value.questions.count() - 1 < index) return
        if (index < 0) return

        val current: Question = _quizState.value.questions.get(index).question
        val answers: List<String> = current.incorrect_answers + current.correct_answer

        _quizState.update { newState ->
            newState.copy(
                currentQuestionIndex = index,
                currentQuestion = QuestionUiModel(
                    question = current.question,
                    correctAnswer = current.correct_answer,
                    answers = answers.shuffled()
                ),
            )
        }
    }

    fun reload() {
        if (_quizState.value.isLoading) return

        viewModelScope.launch {
            _quizState.update { newState ->
                newState.copy(
                    isLoading = true
                )
            }

            val result = repository.fetchQuestions(
                amount = _quiz.value?.amount ?: 10,
                category = _quiz.value?.category,
                difficulty = _quiz.value?.difficulty,
                type = _quiz.value?.type
            )

            _quizState.update { newState ->
                if (result.isSuccess) {
                    val questions: List<Question> = result.getOrDefault(emptyList())

                    newState.copy(
                        isLoading = false,
                        questions = questions.map { question ->
                            QuestionItem(
                                question = question,
                                wasRight = false
                            )
                        },
                        currentQuestionIndex = 0,
                        totalQuestions = questions.count(),
                        score = 0
                    )
                } else {
                    newState.copy(
                        isLoading = false,
                        error = "Error while loading the quiz..."
                    )
                }

            }

            loadQuestion(0)
        }
    }

    init {
        viewModelScope.launch {
            _quiz.value = repository.getQuizById(quizId)
            reload()
        }
    }

    fun submitAnswer(answer: String) {
        Log.d("QUIZ", "LOG answer")

        if (answer == _quizState.value.currentQuestion?.correctAnswer) {
            val updatedList = _quizState.value.questions.toMutableList()
            updatedList[_quizState.value.currentQuestionIndex] = QuestionItem(
                question = updatedList[_quizState.value.currentQuestionIndex].question,
                wasRight = true
            )

            _quizState.update { newState ->
                newState.copy(
                    score = newState.score + 1,
                    questions = updatedList
                )
            }
            //Play some cool sound
        } else {
            //Play some less-cool sound
        }

        if(_quizState.value.currentQuestionIndex + 1 >= _quizState.value.totalQuestions) {
            viewModelScope.launch {
                val result = repository.storeQuizResult(
                    MyConfiguration.loggedInUser?.id ?: 0,
                    quizId,
                    _quizState.value.score
                )

                onFinish(result.id)
            }
        } else {
            loadQuestion(_quizState.value.currentQuestionIndex + 1)
        }
    }

    companion object {
        fun provideFactory(quizId: Int, onFinish: (resultId: Int) -> Unit): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TriviaApplication

                QuizScreenViewModel(
                    repository = application.repository,
                    quizId = quizId,
                    onFinish = onFinish
                )
            }
        }
    }
}

