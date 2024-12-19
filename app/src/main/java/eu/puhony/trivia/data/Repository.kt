package eu.puhony.trivia.data

import android.util.Log
import eu.puhony.trivia.api.Question
import eu.puhony.trivia.api.TriviaApi
import eu.puhony.trivia.data.quiz.Quiz
import eu.puhony.trivia.data.quiz.QuizDao
import eu.puhony.trivia.data.quizResults.QuizResult
import eu.puhony.trivia.data.quizResults.QuizResultDao
import eu.puhony.trivia.data.users.User
import eu.puhony.trivia.data.users.UserDao
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeoutException

class Repository(
    private val api: TriviaApi,
    private val userDao: UserDao,
    private val quizDao: QuizDao,
    private val quizResultDao: QuizResultDao,
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

    suspend fun storeQuizResult(userId: Int, quizId: Int, score: Int) : QuizResult {
        val result = QuizResult(
            userId = userId,
            quizId = quizId,
            score = score,
            completedAt = System.currentTimeMillis()
        )

        quizResultDao.insertQuizResult(result)

        return result
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
        Log.d("Err", "Fetch started")
        withTimeout(5000L) { // Set timeout to 5 seconds
            val response = api.getQuestions(amount, category, difficulty, type)
            val questions = response.results
            Result.success(questions)
        }
    } catch (e: TimeoutCancellationException) {
        Log.d("Err", "Fetch timed out")
        Result.failure(TimeoutException("Request timed out"))
    } catch (e: Exception) {
        Log.d("Err", "Fetch failed: ${e.message}")
        Result.failure(e)
    }
}