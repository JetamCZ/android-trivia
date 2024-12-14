package eu.puhony.trivia.data

import android.util.Log
import eu.puhony.trivia.api.Question
import eu.puhony.trivia.api.TriviaApi
import eu.puhony.trivia.data.quiz.Quiz
import eu.puhony.trivia.data.quiz.QuizDao
import eu.puhony.trivia.data.users.User
import eu.puhony.trivia.data.users.UserDao
import kotlinx.coroutines.flow.Flow

class Repository(
    private val api: TriviaApi,
    private val userDao: UserDao,
    private val quizDao: QuizDao
) {
    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    val allQuizes: Flow<List<Quiz>> = quizDao.getAll()

    suspend fun getOrCreateUser(username: String): User {
        // Check if user exists
        val existingUser = userDao.getUserByUsername(username)

        if (existingUser != null) {
            return existingUser
        }

        // Create and insert a new user
        val newUser = User(username = username)
        userDao.insert(newUser)
        return newUser //.copy(id = userId.toString().toInt())
    }

    suspend fun getQuizById(quizId: Int): Quiz? {
        return quizDao.getById(quizId)
    }

    suspend fun fetchQuestions(
        amount: Int,
        category: Int?,
        difficulty: String?,
        type: String?
    ): Result<List<Question>> = try {
        val response = api.getQuestions(amount, category, difficulty, type)
        Log.d("LOADING", "QUESTIONS fetcher!")
        val questions = response.results //.map { it.toEntity() }

        Result.success(questions)
    } catch (e: Exception) {
        Log.d("LOADING", "fetching error!")
        Log.d("LOADING", e.message ?: "")
        Result.failure(e)
    }
}